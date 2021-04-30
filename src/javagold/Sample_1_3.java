package javagold;

public class Sample_1_3 {
    class SuperA {
    } // スーパークラスA

    final class SuperB {
    } // スーパークラスB

    class SuperC {
        void print() {
        }
    } // スーパークラスC

    class SuperD {
        final void print() {
        }
    } // スーパークラスD

    class SubA extends SuperA {
    } // サブクラスA

    //class SubB extends SuperB{}　// サブクラスB final指定したclassをもとにサブクラスは作成できない
    class SubC extends SuperC {
        void print() {
        }
    } // サブクラスC
    // class SubD extends SuperD{void print(){}} // サブクラスD　final指定したメソッドをオーバーライドできない
}
