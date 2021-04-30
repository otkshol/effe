package javagold;

public class Sample_1_7 {
    public static void main(String[] args) {
        Card1 card = Card1.SPADES;
        switch (card){
            case SPADES:
            case CLUBS:
                System.out.println("brack");
                break;
            case DIAMONDS:
            case HEARTS:
                System.out.println("red");
        }
    }
}
