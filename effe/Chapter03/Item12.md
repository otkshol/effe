# toStringを常にオーバライドする

`Object.toString`が返す文字列は「クラス名 + `@` + 16進数ハッシュコード」であり、人間には意味がわかりにくい。
```
getClass().getName() + '@' + Integer.toHexString(hashCode())
```
（参考）[`Object.toString`のJavadoc](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Object.html#toString--)

---
## toStringの一般契約（２つの努力目標のイメージ）
- **人間とって意味が分かる文字列を返す**
- **すべてのサブクラスでtoStringをオーバライドをする**

具体例
```
{Jenny=PhoneNumber@abdbd} より　{Jenny=707-867-5309}のほうが望ましい
```
---

## toStringが実行されるタイミング
- println
- printf
- 文字列結合演算子(+)
- asssertにオブジェクトが渡される
- デバッガでオブジェクトが表示される(例:EclipceのInspection)

---
## 優れたtoStringメソッドをPhoneNumberに提供した場合

```
System.out.println("Failed to connect: " + phoneNumber);
```
上記の場合
"+"演算子でtoStringが実行されて引数がStringのprintlnが実行されている

<補論> `System.out.println`の読み解き方
- Systemは大文字始まりなのでクラス確定
- クラス名に.が続いた時点でstaticメソッドかstaticフィールドが確定
- ()の有無で判断（()があればstaticメソッド確定なければstaticフィールド確定）
- staticフィールドの次に.が続いた時点で基本データ型ではないことが確定。
- さらに()があるので、インスタンスメソッドであることが確定する
- まとめると、Systemクラスのstaticフィールドoutのインスタンスメソッドであるprintln(String string)が実行されている
- outの型はJavadocを確認すると、printStreamであることが分かる

---
## 注意点
toStringは意味のある情報を全て含む必要がある。例えば、３つのフィールドを用いたequals()なのに2つのフィールドのみの情報を出力すると下記のようなメンテナンスが難しい内容になってしまう。。。
```
Assertion failure: expected {abs, 123}, but was {abc, 123}.
```

---
## toString戻り値のドキュメンテーション
電話番号、行列などの値クラス(value class)に対しては基本的に明示したほうが良い。

- メリット
    - 形式を明示して、対応するスタティックファクトリメソッドやコンストラクタを提供すとオブジェクトと文字列表現の相互変換ができて良い。（例えば、カンマ区切りで出力するようにしてcsv形式への出力とcsv形式をオブジェクトに変換するメソッドの両方を提供して相互変換を可能にすると良い。）
- 活用している例（ #TODO toString()とvaluOf()のことを指している理解であっているか確認）
    - BigInteger
    - BigDecimal
    - Integer
    - Double

---
- 形式を明示する注意点
    - **明示した形式を変更できないので、変更可能性も含めてドキュメントに明示するべき。**（著者のJoshua Bloch自身がこれによるトラブルに巻き込まれた可能性が高い...と感じるほど念入りに忠告している。）
    - **toStringで返す全てのフィールドへのアクセッサ（gettterメソッドなど）を提供するべき。**
        - これをやらないと実装者に文字列の解析（split()使って取り出したりなどの負担）をかけることになる。
    - staticのユーティリティクラス、列挙した定数をそのまま返すenum型は内部実装でtoStringを既に持っているので、実装者がtoStringをオーバーロードする必要はない。

---
## toString()の自動生成
- toString()もGoogleのAutoValueを使用してequals(),hashCode()と同様に自動生成可能。IDEも同様に自動生成可能。しかし、人間に分かりやすい表現までは自動生成してくれないので実装者の手で実装するべき。
### toString()のマシな順番
```
実装者の手でtoString()をオーバーライド > AutoValueやIDEを用いたtoString()の自動生成　> ObjectのtoString()を使用する
```

---
## まとめ
インスタンス化可能なクラスかつ親クラスがObjectのtoString()をオーバーライドしていないものは、常にオーバーライドする。そうすることで人間に分かりやすいログを出力することができてメンテナンスが楽になる。