#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int matcher(char* argv,char* c);
void encrypt(char numArray[] , long long pk, long long n);
void decrypt(long long longArray[], long long d, long long n);
long long lcm(long long a, long long b);
long long gcd(long long a, long long b);
long long gcdEx(long long l, long long pk, long long n);
int static numSum=0;
int main(int argc, char* argv[]){
    // Your code here!
    if(argc<2){
        printf("Enter ");
        return 0;
    }
    if(matcher(argv[1],"-g")){
        //generate
    }else if(matcher(argv[1],"-e") && argc == 4){
        long long pk = atoll(argv[2]);
		long long n  = atoll(argv[3]);
		printf("Enter text\n");
		char numArray[255];
		if(scanf("%255[^\n]%*[^\n]%*c",numArray) != 1){
			printf("Error.");
			return 1;
		}
		printf("\n%s\n",numArray);
		encrypt(numArray,pk,n);
		//long longArray[255] = encrypt(numArray,pk,n);
		
    }else if(matcher(argv[1],"-d") && argc == 4){
		long long sk = atoll(argv[2]);
		long long n  = atoll(argv[3]);
		printf("Enter nums\n");
		char cArray[255];
		if(scanf("%255[^\n]%*[^\n]%*c",cArray) != 1){
			printf("Error.");
			return 1;
		}
		long long longArray[255];
		char *ptr;
		char *endptr;
		ptr = strtok(cArray,",");
		longArray[0] = strtoll(ptr,&endptr,0);
		int i=1;
		while(ptr != NULL){
			ptr = strtok(NULL,",");
			if(ptr !=NULL){
				longArray[i] = strtoll(ptr,&endptr,0);
				i++;
			}
		}
		numSum =i;
		//printf("\n%s\n",numArray);
		decrypt(longArray,sk,n);
/*
		char c[255];
		c = decrypt(longArray,sk,n);
		int i;
		for(i=0;i<255 && i < (sizeof(c)/sizeof(char));i++){
			printf("\n%c\n",c[i]);
		}
*/
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
void encrypt(char numArray[], long long pk,long long n){
	int i;
	
	long long longArray[255];
	for(i=0;numArray[i] != '\0';i++){
		int k = 0;
		k = (numArray[i]);
		longArray[i] = k;
		int j;
		for(j=0;j<pk-1;j++){
			longArray[i]=(longArray[i]*k)%n;
			//if(j<10){
			//	printf("%d:%lld\n",j,longArray[i]);
			//}
		}
	}
	printf("Encrypt\n");
	int k;
	for(k=0;k<i;k++){
		printf("%ld,",longArray[k]);
	}
	return ;
}
void decrypt(long long longArray[], long long d, long long n){
	char intArray[255];
	int i;
	printf("decrypt\n");
	for(i=0;i<numSum;i++){
		long long k = longArray[i];
		long long l = longArray[i];
		long long j;
		for(j=0;j<d-1;j++){
			l=((l*k)%n);
		}
		intArray[i]=(char)l;
		printf("%c",intArray[i]);
	}
	return;
}
long long lcm(long long a, long long b){
	a--;
	b--;
	return a*b/gcd(a,b);
}
long long gcd(long long a, long long b){
	long long temp;
	while(a%b!=0){
		temp = b;
		b = a%b;
		a=temp;
	}
	return b;
}
long long gcdEx(long long l, long long pk, long long n){
	long long d;
	for(d=1;;d++){
		if((pk*d)%l==1){
			return d;
		}
	}
}



