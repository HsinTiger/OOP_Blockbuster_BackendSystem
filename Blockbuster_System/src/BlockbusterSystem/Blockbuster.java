package BlockbusterSystem;

import java.util.ArrayList;
import java.util.List;

//以JAVA撰寫//
public class Blockbuster {
    public static void main(String[] args) {
        Customer April = new Customer("April");
        Customer Joe =  new Customer("Joe");

        final int Stock_For_EachVideo = 10;

        Video Wonder＿Woman = new OldVideo("神力女超人","old",Stock_For_EachVideo);
        Video Justice＿League = new NewVideo("正義聯盟","new",Stock_For_EachVideo);
        Video Thor＿3 = new NewVideo("雷神索爾3","new",Stock_For_EachVideo);
        April.BorrowVideo(Wonder＿Woman,3,1);
        Joe.BorrowVideo(Wonder＿Woman,2,1);
        April.BorrowVideo(Justice＿League,5,1);
        Joe.BorrowVideo(Thor＿3,1,1);

        April.print_statement();
        Joe.print_statement();
        April.print_TotalBonusPoint();
        Joe.print_TotalBonusPoint();

        //check 各影片庫存是否正確
        System.out.println("『神力女超人』所剩下庫存： " + Wonder＿Woman.getStock());
        System.out.println("『正義聯盟』所剩下庫存： " + Justice＿League.getStock());
        System.out.println("『雷神索爾3』所剩下庫存： " + Thor＿3.getStock());
    }
}


abstract class Video{
    private String name;
    private String type;
    private int stock;

    //**ExtraSetting**//
    protected int price=0;
    protected int deadline=0;
    protected int extraRentPerDay=0;
    protected double bonusPoint=0;

    public Video(String Name, String Type, int Stock){
        this.name = Name;
        this.type = Type;
        this.stock = Stock;
        ExtraSetting();   //多型實作在各個子class
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public int getStock(){
        return stock;
    }
    public void DecreaseStock(int amount){
        stock -= amount;
    }
    public void IncreaseStock(int amount){
        stock += amount;
    }


    public int getPrice(){
        return price;
    }
    public int getDeadline(){
        return deadline;
    }
    public int getExtraRentPerDay(){
        return extraRentPerDay;
    }
    public double getBonusPoint(){
        return bonusPoint;
    }

    //**多型運用**//
    abstract public void ExtraSetting();
}
class NewVideo extends Video{
    NewVideo(String Name, String Type, int Stock){
        super(Name, Type, Stock);
    }
    public void ExtraSetting(){
        /*依據type不同,初始化租任價錢、期限、續借、回饋點數,設定price&deadline&extraRentPerDay&bonusPoint}多型*/
        price = 3;
        deadline = 1;
        extraRentPerDay = 2;
        bonusPoint = 2.0;
    }
}
//**同理NewVideo**//
class OldVideo extends Video{
    OldVideo(String Name, String Type, int Stock){
        super(Name, Type, Stock);
    }
    public void ExtraSetting(){
        /*依據type不同,初始化租任價錢、期限、續借、回饋點數,設定price&deadline&extraRentPerDay&bonusPoint}多型*/
        price = 2;
        deadline = 3;
        extraRentPerDay = 1;
        bonusPoint = 1.0;
    }
}
class ChildVideo extends Video{
    ChildVideo(String Name, String Type, int Stock){
        super(Name, Type, Stock);
    }
    public void ExtraSetting(){
        /*依據type不同,初始化租任價錢、期限、續借、回饋點數,設定price&deadline&extraRentPerDay&bonusPoint}多型*/
        price = 2;
        deadline = 5;
        extraRentPerDay = 1;
        bonusPoint = 0.5;
    }
}


class Customer {
    private String Customer_Name;
    private int TotalComsume_in_Blockbuster;
    private float TotalBonusPoint;

    private List<RentRecord> RentRecords = new ArrayList<RentRecord>();  //記錄客戶租過的每部影片與此片花費

    public Customer(String Customer_Name) {
        this.Customer_Name = Customer_Name;
        TotalComsume_in_Blockbuster = 0;
        TotalBonusPoint = 0;
    }

    public void BorrowVideo(Video WhichVideo, int BorrowDays, int amount) {
        if (amount > WhichVideo.getStock()) {
            System.out.println("親愛的"+Customer_Name+",您所借電影『"+WhichVideo.getName()+"』庫存不足，請重新設定欲租借數量!!");
        } else {
            System.out.println("親愛的"+Customer_Name+",您所借電影『"+WhichVideo.getName()+"』尚有庫存，租借成功!!稍等系統設定中");
            TotalBonusPoint += WhichVideo.getBonusPoint();
            int extra_price = 0;
            if (BorrowDays > WhichVideo.getDeadline()) { //若借的天數超過基本時數
                extra_price = WhichVideo.getExtraRentPerDay() * (BorrowDays - WhichVideo.getDeadline());//多幾天收多少錢
            }
            RentRecord t = new RentRecord(WhichVideo, extra_price + WhichVideo.price, BorrowDays);
            RentRecords.add(t);
            WhichVideo.DecreaseStock(amount);
        }
    }
    public void print_statement(){
            System.out.println("----------查閱ing "+ Customer_Name + " 的歷來租借紀錄:----------");
            for (int i = 0; i < RentRecords.size(); i++) {
                {
                    int temp = RentRecords.get(i).getComsume_in_this_video();
                    System.out.println("片名:" + RentRecords.get(i).getVideo().getName() + "，價錢:" + temp);
                    TotalComsume_in_Blockbuster += temp;
                }
            }
            System.out.println("過去租片總額:" + TotalComsume_in_Blockbuster);
    }
    public void print_TotalBonusPoint(){
        System.out.println("----------查閱ing " + Customer_Name + " 過去總累積點數:----------");
        System.out.println(TotalBonusPoint);
    }
}
class RentRecord {
    private Video video;
    private int comsume_in_this_video;
    private int borrowDays;

    public RentRecord(Video video, int comsume_in_this_video, int borrowDays) {
        this.video = video;
        this.comsume_in_this_video = comsume_in_this_video;
        this.borrowDays = borrowDays;
    }

    public Video getVideo() {
        return video;
    }

    public int getComsume_in_this_video() {
        return comsume_in_this_video;
    }

    public int getBorrowDays() {
        return borrowDays;
    }
}

