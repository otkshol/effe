## *try-finally*よりも*try-with-resource*を選ぶ

Javaライブラリにはcloseメソッドを呼び出して手作業でcloseする必要がある資源がある
- 例
    - `InputStream`
    - `OutputStream`
    - `java.sql.Connection`

```
// try-finallyの例

```


```
// 複数資源に対するtry-finally

```


Java７から*try-with-resource*

```
// try-with-resourceの例

```


```
// 複数資源に対するtry-with-resource

```



```
// catch節を持つtry-with-resource

```
