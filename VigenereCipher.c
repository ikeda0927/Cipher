#include <stdio.h>
#define true 1
#define false 0

const int ALP_SUM = 26;//アルファベットの数
const char lowerCase[26] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
const char upperCase[26] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
int keySize;//鍵のサイズ
int sentenceSize;//暗号化、復号対象の文字列
int i;//for文用の変数
char key[512];//鍵のcharの配列
int keyNum[512];//鍵のnumの配列
char sentence[512];//暗号化、復号対象のcharの配列
int sentenceNum[512];//暗号化、復号対象のcharの配列が数字に変換されたやつ
int encryptedNum[512];//暗号化、もしくは復号済みのnumの配列
char encryptedSentence[512];//暗号化、もしくは復号済みのcharの配列
char encrypt[] = "Encrypt";//比較用文字列
char decrypt[] = "Decrypt";//比較用文字列
char getkey[] = "getKey";//比較用文字列
int scomp(char a[],char b[]);//charの配列の比較
void scopy(char dst[],char src[]);//charの配列のコピー
int converttonums(char srcarray[],int numarray[]);//charの配列からnumの配列へ
int main(int argc, char* argv[]){
    if(argc < 4){
		printf("Enter 'key' and 'SENTENCE' and 'Encrypt or Decrypt' as arguments.\n");
		printf("Or to extract the key, Enter 'the sent SENTENCE' and 'plain SENTENCE' and 'getKey'.\n");
		return 0;
	}
	//文字列のコピー
	scopy(key,argv[1]);
	scopy(sentence,argv[2]);
	//鍵から数列を得る
	keySize = converttonums(key,keyNum);
	//平文から数列を得る
	sentenceSize = converttonums(sentence,sentenceNum);
	//暗号化 or 復号
	int k;
	int m;
	if(scomp(argv[3],encrypt)){//暗号化
		for(k =0,m=0;k<sentenceSize;k++){
			encryptedNum[k] = keyNum[m] + sentenceNum[k];
			if(encryptedNum[k]>ALP_SUM-1){
				encryptedNum[k] = encryptedNum[k]-ALP_SUM;
			}
			m++;
			if(m>keySize-1){
				m=0;
			}
		}
		printf("EncryptedSentence:");
	}else if(scomp(argv[3],decrypt)){//復号
		for(k =0,m=0;k<sentenceSize;k++){
			encryptedNum[k] = sentenceNum[k] - keyNum[m];
			if(encryptedNum[k]<0){
				encryptedNum[k] = ALP_SUM+encryptedNum[k];
			}
			m++;
			if(m>keySize-1){
				m=0;
			}
		}
		printf("DecryptedSentence:");
	}else if(scomp(argv[3],getkey)){//鍵の抽出(keyNumやsentenceNum, encryptedNumの名前と配列の中身が一致していない所に注意)
		for(k =0,m=0;k<sentenceSize;k++){
			encryptedNum[k] = keyNum[m] - sentenceNum[k];
			if(encryptedNum[k]<0){
				encryptedNum[k] = ALP_SUM+encryptedNum[k];
			}
			m++;
			if(m>keySize-1){
				m=0;
			}
		}
		printf("The key is included in here :");
	}else{//第三引数に"Encrypt"と"Decrypt"と"getKey"以外の文字列が入力されたとき
		printf("Please set 'Encrypt' or 'Decrypt'.\n",argv[3]);
		return 0;
	}
	for(i=0;i<sentenceSize;i++){//暗号化or復号された文の表示
		printf("%c",upperCase[encryptedNum[i]]);
	}
	return 0;
}
int scomp(char a[],char b[]){
	//charの配列では比較できないため、charにして一文字ずつ比較する
	int i;
	char ca;
	char cb;
	for(i=0;a[i] != '\0';i++){
		ca = (char)a[i];
		cb = (char)b[i];
		if(ca != cb){
			return false;
		}
	}
	cb=b[i];
	if(cb == '\0'){
		return true;
	}else{
		return false;
	}
}
int converttonums(char srcarray[],int numarray[]){
	//a -> 0, b -> 1, .... z -> 25のように変換
	int counter = 0;
	while(*srcarray != '\0'){
		int i;
		for(i = 0;i<ALP_SUM;i++){
			if(*srcarray == lowerCase[i]){
				numarray[counter] = i;
			}else if(*srcarray == upperCase[i]){
				numarray[counter] = i;
			}
		}
		srcarray++;
		counter++;
	}
	return counter;
}
void scopy(char dst[],char src[]){
	//srcの文字列をdstにコピー,文字の中身は同じだがアドレスが異なる。
	while(*src != '\0'){
		//アルファベット以外を無視してコピー
		int i;
		for(i=0;i<ALP_SUM;i++){
			if(lowerCase[i] == *src){
				*dst = *src;
				dst++;
				break;
			}else if(upperCase[i] == *src){
				*dst = *src;
				dst++;
				break;
			}
			
		}
		
				src++;
	}
}
