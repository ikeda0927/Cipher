#include <stdio.h>
#include <stdlib.h>
#define true 1
#define false 0

const int ALP_SUM = 26;//アルファベットの数
const int NUM_SUM = 10;//数字の総数
const char lowerCase[26] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
const char upperCase[26] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
const int num[10] = {'0','1','2','3','4','5','6','7','8','9'};
int weight=12;//ずらすアルファベットと数字の個数の初期値
int alpWeight = 12;
int numWeight = 2;
size_t StrLen(char* str);//入力した文字列の長さを返す関数のプロトタイプ宣言
char replace(char c);
int isLoop = true;
int isCheckAll=false;
int main(int argc, char* argv[]){
	if(argc > 1 && *argv[1] != 'r' && *argv[1] != 'a'){//コマンドライン引数の二番目をweightとして受け取る
		weight = atoi(argv[1]);//コマンドライン引数から受け取ったものはchar*なのでintにする。
		if(argc > 2 && *argv[2] == 'r'){
			alpWeight = weight;
			numWeight = weight;
			while(alpWeight >= ALP_SUM){
				alpWeight-=ALP_SUM;
			}
			while(numWeight >= NUM_SUM){
				numWeight-=NUM_SUM;
			}
			alpWeight = ALP_SUM-alpWeight;
			numWeight = NUM_SUM-numWeight;
		}else{
			alpWeight = weight;
			numWeight = weight;
			while(alpWeight >= ALP_SUM){
				alpWeight-=ALP_SUM;
			}
			while(numWeight >= NUM_SUM){
				numWeight-=NUM_SUM;
			}
		}
		//cout <<"weight:" << weight << " numWeight:" << numWeight << " alpWeight:" << alpWeight << endl;
	}else if(argc > 1 && *argv[1] == 'r'){
		alpWeight = ALP_SUM-alpWeight;
		numWeight = NUM_SUM-numWeight;
		while(alpWeight >= ALP_SUM){
			alpWeight-=ALP_SUM;
		}
		while(numWeight >= NUM_SUM){
			numWeight-=NUM_SUM;
		}
	}else if(argc > 1 && *argv[1] == 'a'){
		isCheckAll=true;
		//cout << "isCheckAll" << endl;
	}
	while(isLoop){
		//cout << "Enter string.(Use '_' not ' ')" << endl;
		printf("Enter string.\n");
        char s1[512];//十分な大きさの配列を用意
		//cin >> s1;//文字列を読み込む
		scanf("%s",s1);
        if(isCheckAll){
			numWeight=1;
			alpWeight=1;
            int k;
			for(k=0;k<ALP_SUM*NUM_SUM/2;k++){
				//cout << "weight : " << k << endl;
				char* s1p = s1;//読み込んだ文字列のポインタをセット
				for(;*s1p != '\0';++s1p){//s1pの先頭アドレスにある文字が終端文字になるまで先頭アドレスをずらす
					*s1p = replace(*s1p);
				}
				printf("%s\n",s1);
                //cout << endl << s1 << endl;
			}
		}else{
			char* s1p = s1;//読み込んだ文字列のポインタをセット
			//int stringLength = (int)StrLen(s1);//文字列の長さをゲット
			//while(stringLength>0){//大体真ん中らへんに矢印を持っていきたい
			//	stringLength-=2;
			//	cout << " " << flush;
			//}
			//cout << "↓ " << weight << flush;//矢印の隣にずらした数を記す
			for(;*s1p != '\0';++s1p){//s1pの先頭アドレスにある文字が終端文字になるまで先頭アドレスをずらす
				*s1p = replace(*s1p);
			}
			//cout << endl << s1 << endl;
            printf("%s\n",s1);
		}
		//cout << "Continue? y/n" << endl;
		printf("Continue? y/n\n");
        char loop;
		//cin >> loop;
		scanf("%s",&loop);
        if(loop=='y'||loop=='Y'){
			isLoop=true;
		}else{
			isLoop=false;
		}
	}
    return 0;
}

char replace(char c){
    int i;
	for(i=0;i<NUM_SUM;i++){
		if(num[i] == c){
			if(NUM_SUM-i<=numWeight){
				return num[numWeight+i-NUM_SUM];
			}else{
				return num[i+numWeight];
			}
		}
	}
	for(i=0;i<ALP_SUM;i++){
		if(lowerCase[i] == c){
			if(ALP_SUM-i<=alpWeight){
				return lowerCase[alpWeight+i-ALP_SUM];
			}else{
				return lowerCase[i+alpWeight];
			}
		}
	}
	for(i=0;i<ALP_SUM;i++){
		if(upperCase[i] == c){
			if(ALP_SUM-i<=alpWeight){
				return upperCase[alpWeight+i-ALP_SUM];
			}else{
				return upperCase[i+alpWeight];
			}
		}
	}
	return c;
}

//size_t StrLen(char* str){//本で見た文字列の長さをゲットするコード
//	size_t i;
//	for(i=0;*(str+i) != '\0';i++){
//		//nop
//	}
//	return i;
//}
