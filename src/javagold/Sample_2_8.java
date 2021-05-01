package javagold;


interface Sample_2_8 {
    // int a; // interfaceの変数宣言は暗黙的にpublic static finalがつくので宣言時に初期化する必要がある
    // protected void methodA(); // interfaceは暗黙的に public abstractがつくのでpublicいがいの修飾子をつけとコンパイルエラーにある
    // final void methodB(); // interfaceメソッドはサブクラスでオーバーライドする必要があるのでfinalもNG
    // static void methodC(); // interfaceにはstaticな抽象メソッドは定義できない
}
