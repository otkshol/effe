package javagold;

public class Sample_1_5 {
    int instanceVal;
    static int staticVal;

    int methodA() {return instanceVal;}
    int methodB() {return  staticVal;}
    // static int methodC() {return instanceVal;}
    static int methodD() {return staticVal;}
    static int methodE() {
        Sample_1_5 obj = new Sample_1_5();
        return obj.instanceVal;}

}
