#include <stdio.h>
#include <stdlib.h>

int matcher(char* argv,char* c);
void encrypt(char numArray[] , long pk, long n);
int main(int argc, char* argv[]){
    // Your code here!
    if(argc<2){
        printf("Enter ");
        return 0;
    }
    if(matcher(argv[1],"-g")){
        //generate
    }else if(matcher(argv[1],"-e") && argc == 4){
        long pk = atol(argv[2]);
		long n  = atol(argv[3]);
		printf("Enter text\n");
		char numArray[255];
		if(scanf("%255[^\n]%*[^\n]%*c",numArray) != 1){
			printf("Error.");
			return 1;
		}
		printf("\n%s\n",numArray);
		encrypt(numArray,pk,n);
		//long longArray[255] = encrypt(numArray,pk,n);
		
    }
    return 0;
}
int matcher(char* argv,char* c){
    
    while(*argv != '\0'){
        if(*argv == *c){
        }else{
            return 0;
        }
        argv++;
        c++;
    }
    if(*c != '\0'){
        return 0;
    }
    return 1;
}
void encrypt(char numArray[], long pk, long n){
	int i;
	long longArray[255];
	for(i=0;numArray[i] != '\0';i++){
		int k = 0;
		k = (numArray[i]);
		longArray[i] = k;
		int j;
		for(j=0;j<pk-1;j++){
			longArray[i]=longArray[i]*k%n;
		}
	}
	printf("Encrypt\n");
	int k;
	for(k=0;k<i;k++){
		printf("%ld,",longArray[k]);
	}
	return ;
}
