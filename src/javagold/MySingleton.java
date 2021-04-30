package javagold;

public class MySingleton {
    private static final MySingleton instance = new MySingleton(); // static final宣言することで一度だけインスタンス化させている
    private MySingleton(){} // コンストラクタはprivateなので外部呼び出しができない
        public static MySingleton getInstance(){ // このメソッドを読んで一個しかないインスタンスを適宜呼び出している。
            return instance;
        }
    }

