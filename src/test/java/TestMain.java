import java.util.Scanner;

public class TestMain {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("请输入是否是会员：是/否？");
        while (input.hasNext()){
            String hy = input.next();
            System.out.println("请输入购物金额：");
            double je = input.nextDouble();

            if ("是".equals(hy)) {//会员
                if (je >= 100 && je < 200) {
                    System.out.println("会员，满100打9折，实付金额：" + je * 0.9);
                } else if (je >= 200) {
                    System.out.println("会员，满200打75折，实付金额：" + je * 0.75);
                } else {
                    System.out.println("会员，不满100不打折，实付金额：" + je);
                }
            } else {//非会员
                if (je >= 200) {
                    System.out.println("非会员，满200打8折，实付金额：" + je * 0.8);
                } else {
                    System.out.println("非会员，不满200不打折");
                }
            }
            System.out.println("请输入是否是会员：是/否？");

        }

    }
}
