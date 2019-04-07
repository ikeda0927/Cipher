暗号用のリポジトリ

・シーザー暗号の暗号化、復号コード  
(コマンドライン引数無しで実行すると12ずらされ、コマンドライン引数の一つ目に数字を指定すると指定した数字分ずらされ、数字では無く'd'を指定すると、コマンドライン引数無しで暗号化された文字列を復号することが出来、コマンドライン引数の一つ目に数字、二つ目に'd'を指定すると、指定した数字分ずらされた文字列を復号することが出来る。また、コマンドライン引数の一つ目に'a'を指定すると、取敢えず全パターンの変換を行なう。)  
　CeasarCipher.c  
 
・ヴィジュネル暗号の暗号化、復号コード  
(使い方は引数無しで実行すると表示される)  
　VigenereCipher.c  
 
・エニグマ  
(使い方は引数として3文字のアルファベット(鍵)を入力するとその後は暗号化、もしくは復号したい文字列を入力するだけ。暗号化、復号したい文字列には空白が含まれていても構わないが、鍵にはアルファベット以外入れないこと。)

・RSA  
引数に何も入れずに実行すると使い方がみれます.  
-gと引数に入れて実行すると、100000以下の素数(擬素数)が二つ生成され、そこから二つの素数の和(n)と公開鍵(public key)と秘密鍵(secret key)が生成されます。   
  
第一引数に-e、第二引数に公開鍵、第三引数にnを入力して実行すると、文字列 ~~(文字と空白のみ可)~~ (漢字や数字もOKです。)の入力を要求されます。  
すると、暗号化された文字列が","区切りの数字の配列形式(例　1,2,3,4)で出力されます。  
  
第一引数に-d、第二引数に秘密鍵、第三引数にnを入力して実行すると、暗号化された文字列(暗号化した時に出力された形式で(例　1,2,3,4))の入力を要求されます。  
すると、復号された文字列が出力 ~~(非常に低確率で失敗(擬素数が原因なので鍵生成からやり直し))~~ されます。  
  

