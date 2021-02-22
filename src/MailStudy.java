import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

public class MailStudy {
    public static void main(String[] args) {

        // ダメなパターン（アドレスが全てASCII文字かつRFC822で定義された特殊文字を含んでいるパターン）
        //String address = "re:otake";
        // いけるパターン
        //String address = "reotake";
        String address = "株式会社re:otake";
        String personal = "otake@gmail.com";
        try{
            // 修正前
            String oldMailAddress = MimeUtility.encodeText(address,"iso-2022-jp","B") + " <" + personal + ">";
            InternetAddress.parse(oldMailAddress);
            System.out.println(oldMailAddress);
            // 修正後
            // ASCII文字のみの場合、InternetAddressによってaddress部分にダブルクォーテーション(")が補完される
            // 非ASCII文字が含まれる場合address部分がBase64エンコードされる
            InternetAddress newMailAddress = new InternetAddress(personal,MimeUtility.encodeText(address,"iso-2022-jp","B"),"iso-2022-jp");
            System.out.println(newMailAddress);
        } catch(AddressException e){
            e.printStackTrace();
            System.out.println("アドレスがRFC822に準拠していないため例外発生");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            System.out.println("エンコードの例外発生");
        }
    }
}
