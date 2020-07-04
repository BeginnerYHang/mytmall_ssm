package com.yuanhang.controller;

import com.github.pagehelper.PageHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yuanhang.pojo.*;
import com.yuanhang.service.*;
import com.yuanhang.service.impl.CategoryServiceImpl;
import com.yuanhang.util.Message;
import com.yuanhang.util.comparator.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-05 9:30
 */
@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ReviewService reviewService;

    @RequestMapping("/forehome")
    public String home(Model model) {
        List<Category> cs = categoryService.findAll();
        model.addAttribute("cs", cs);
        categoryService.fillProductByRow(cs);
        categoryService.fillProduct(cs);
        return "fore/home";
    }

    @RequestMapping("/forecategory")
    //根据传入的sort对产品进行排序,需要实现几个比较器
    public String category(int cid,String sort,Model model){
        Category category = categoryService.findOne(cid);
        List<Product> ps = category.getProducts();
        if (null != sort){
            switch (sort){
                case "review":
                    ps.sort(new ProductReviewComparator());
                    break;
                case "date":
                    ps.sort(new ProductDateComparator());
                    break;
                case "saleCount":
                    ps.sort(new ProductSaleComparator());
                    break;
                case "price":
                    ps.sort(new ProductPriceComparator());
                    break;
                default:
                    ps.sort(new ProductAllComparator());
            }
        }
        category.setProducts(ps);
        model.addAttribute("c",category);
        return "fore/category";
    }

    @RequestMapping("/foreregister")
    public @ResponseBody String register(HttpSession session, User user,String checkCode){
        String serverCode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (!checkCode.equalsIgnoreCase(serverCode)){
            //在参数里有 user,就会自动放到作用域里去了
            return "验证码输入有误,点击刷新换一个试试吧";
        }
        if (userService.isExist(user.getUsername())){
            return "用户名已存在,请重新输入";
        }
        //防止用户恶意操作
        String name = HtmlUtils.htmlEscape(user.getUsername());
        user.setUsername(name);
        userService.add(user);
        return "success";
    }

    @RequestMapping("/forelogin")
    public String login(String username,String password, Model model,HttpSession session){
          username = HtmlUtils.htmlEscape(username);
          User user = userService.get(username,password);
          if (user == null){
              model.addAttribute("msg","您输入的账号或密码有误,请重新输入");
              return "fore/login";
          }
          session.setAttribute("user",user);
          return "redirect:forehome";
    }

    @RequestMapping("/foreloginAjax")
    @ResponseBody
    public String loginAjax(User user,String checkCode,HttpSession session){
        String checkCodeServer = (String) session.getAttribute("CHECKCODE_SERVER");
        System.out.println("------------\n\n" + checkCodeServer );
        session.removeAttribute("CHECKCODE_SERVER");
        if (!checkCode.equalsIgnoreCase(checkCodeServer)){
            return "您输入的验证码有误,请刷新一下再试试吧";
        }
        user.setUsername(HtmlUtils.htmlEscape(user.getUsername()));
        User realUser = userService.get(user.getUsername(),user.getPassword());
        if (null == realUser){
            return "您的账号或密码有误,请检查之后重新输入";
        }
        session.setAttribute("user",realUser);
        return "success";
    }

    @RequestMapping("/forelogout")
    @ResponseBody
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "exit";
    }

    @RequestMapping("/foreproduct")
    public String product(int pid,Model model){
        Product p = productService.findOne(pid);
        model.addAttribute("p",p);
        List<PropertyValue> pvs = propertyValueService.findAll(pid);
        model.addAttribute("pvs",pvs);
        List<Review> reviews = reviewService.findAll(pid);
        model.addAttribute("reviews",reviews);
        return "fore/product";
    }

    @RequestMapping("/forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session){
        if (null != session.getAttribute("user")){
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/foresearch")
    public String search(String keyword, Model model){
        //最多显示20条
        PageHelper.offsetPage(0,20);
        List<Product> ps = productService.search(keyword);
        model.addAttribute("ps",ps);
        return "fore/searchResult";
    }

    /**
     * 该用户点击立即购买并且通过验证后,如果该产品未生成订单(oid=null)且已有订单项,则在原来的订单数量添加
     * 否则会在OrderItem表中新增一条记录
     */
    @RequestMapping("/forebuyone")
    public String buyOne(int pid,int num,HttpSession session){
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = orderItemService.findAllByUid(user.getId());
        int oiid = 0;
        //用来标识是否找到该订单;
        boolean isFound = false;
        for (OrderItem orderItem : ois) {
            //表示未生成订单但已有订单项的产品
            if (orderItem.getPid() == pid && orderItem.getOid() == null){
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                oiid = orderItem.getId();
                isFound = true;
                break;
            }
        }
        //如果没有找到则在orderitem表中新增一项
        if (!isFound){
            OrderItem oi = new OrderItem();
            oi.setPid(pid);
            oi.setUid(user.getId());
            oi.setNumber(num);
            orderItemService.insert(oi);
            oiid = oi.getId();
        }
        //添加完orderitem表项后进行购买操作
        return "redirect:forebuy?oiids=" + oiid;
    }

    @RequestMapping("/forebuy")
    public String buy(int[] oiids,Model model,HttpSession session){
        List<OrderItem> ois = new ArrayList<>();
        double total = 0;
        for (int oiid : oiids) {
            OrderItem oi = orderItemService.findOne(oiid);
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            ois.add(oi);
        }
        session.setAttribute("ois",ois);
        model.addAttribute("total",total);
        return "fore/buy";
    }

    @RequestMapping("/foreaddCart")
    @ResponseBody
    public String addCart(int pid,int num,HttpSession session){
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = orderItemService.findAllByUid(user.getId());
        int oiid = 0;
        //用来标识是否找到该订单;
        boolean isFound = false;
        for (OrderItem orderItem : ois) {
            //表示未生成订单但已有订单项的产品
            if (orderItem.getPid() == pid && orderItem.getOid() == null){
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                oiid = orderItem.getId();
                isFound = true;
                break;
            }
        }
        //如果没有找到则在orderitem表中新增一项
        if (!isFound){
            OrderItem oi = new OrderItem();
            oi.setPid(pid);
            oi.setUid(user.getId());
            oi.setNumber(num);
            orderItemService.insert(oi);
            oiid = oi.getId();
        }
        return "success";
    }

    @RequestMapping("/forecart")
    public String cart(HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = orderItemService.findAllByUid(user.getId());
        Iterator<OrderItem> iterable;
        //关于ArrayList删除满足某些指定元素问题
        for (iterable = ois.iterator();iterable.hasNext();){
            OrderItem oi = iterable.next();
            if (oi.getOid() != null){
                iterable.remove();
            }
        }
        model.addAttribute("ois",ois);
        return "fore/cart";
    }

    @RequestMapping("/forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(int pid, int num,HttpSession session){
        User user = (User) session.getAttribute("user");
        List<OrderItem> oi = orderItemService.findAllByUid(user.getId());
        boolean isFound = false;
        for (OrderItem orderItem : oi) {
            if (orderItem.getPid().intValue() == pid && orderItem.getOid() == null){
                orderItem.setNumber(num);
                isFound = true;
                orderItemService.update(orderItem);
                break;
            }
        }
        return isFound?"success":"fail";
    }

    @RequestMapping("/foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(int oiid){
        orderItemService.delete(oiid);
        return "success";
    }

    @RequestMapping("/forecreateOrder")
    public String createOrder(String province,String city,String district,Order o,HttpSession session){
        User user = (User ) session.getAttribute("user");
        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
        o.setAddress(province + city + district + o.getAddress());
        o.setUid(user.getId());
        o.setCreateDate(new Date());
        o.setStatus(OrderService.WAIT_PAY);
        //以当前时间和四位随机数生成订单码
        o.setOrderCode(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000));
        float total = orderService.add(o,ois);
        return "redirect:forealipay?oid="+o.getId() +"&total="+total;
    }

    @RequestMapping("/forealipay")
    public String alipay(int oid,float total){
        //可以在页面中利用{param.XXX}拿到参数
        BigDecimal bd = new BigDecimal(total);
        //保留两位小数,进行四舍五入
        total = bd.setScale(2,4).floatValue();
        return "fore/alipay";
    }

    //支付完成后修改订单的状态以及支付时间
    //在支付完成后更新商品库存
    @RequestMapping("/forepayed")
    public String payed(int oid,float total,Model model){
        Order order = orderService.findOne(oid);
        orderService.fill(order);
        List<OrderItem> ois = order.getOrderItems();
        for (OrderItem orderItem : ois) {
            Product product = orderItem.getProduct();
            product.setStock(product.getStock() - orderItem.getNumber());
            productService.updateProduct(product);
        }
        order.setStatus(OrderService.WAIT_DELIVERY);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o",order);
        return "fore/payed";
    }

    //我的订单页
    @RequestMapping("/foreorder")
    public String order(HttpSession session,Model model){
        User user = (User)session.getAttribute("user");
        List<Order> os = orderService.findAllExcludeStatus(user.getId(),"delete");
        model.addAttribute("os",os);
        return "fore/order";
    }

    @RequestMapping("/foredeleteorder")
    @ResponseBody
    public String deleteOrder(int oid){
        Order order = orderService.findOne(oid);
        order.setStatus(OrderService.DELETE);
        //将该Order的状态改为delete让用户无法看见,并不执行数据库的删除操作
        orderService.update(order);
        return "seccess";
    }

    @RequestMapping("/foreconfirmPay")
    public String confirmPay(int oid,Model model){
        Order order = orderService.findOne(oid);
        orderService.fill(order);
        model.addAttribute("o",order);
        return "fore/confirmPay";
    }

    //用户确认收货时将货款打至卖家并修改数据库订单信息
    @RequestMapping("/foreorderConfirmed")
    public String orderConfirmed(int oid){
        Order order = orderService.findOne(oid);
        order.setConfirmDate(new Date());
        order.setStatus(OrderService.WAIT_REVIEW);
        orderService.update(order);
        return "fore/orderConfirmed";
    }

    @RequestMapping("/forereview")
    public String review(int oid,Model model,HttpSession session){
        Order order = orderService.findOne(oid);
        orderService.fill(order);
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = order.getOrderItems();
        List<Boolean> isReviews = new ArrayList<>();
        for (OrderItem orderItem : ois) {
            reviewService.fill(orderItem.getProduct());
            isReviews.add(reviewService.isReview(orderItem.getPid(),user.getId()));
        }
        model.addAttribute("ois",ois);
        model.addAttribute("o",order);
        model.addAttribute("irs",isReviews);
        return "fore/review";
    }

    @RequestMapping("/foredoreview")
    public String doReview(Review review,int oid,HttpSession session){
        Order order = orderService.findOne(oid);
        order.setStatus(OrderService.FINISH);
        orderService.update(order);
        review.setCreateDate(new Date());
        User user = (User)session.getAttribute("user");
        review.setUid(user.getId());
        reviewService.add(review);
        return "redirect:forereview?oid=" + oid;
    }

    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse response,HttpSession session) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0, 0, width, height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        session.setAttribute("CHECKCODE_SERVER", checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体", Font.BOLD, 24));
        //向图片上写入验证码
        g.drawString(checkCode, 15, 25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVMXYZabcdefghijklmnopqrstuvwxyz";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }
}
