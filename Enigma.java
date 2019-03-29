import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Enigma {
	
	static final int ALP_SUM = 26;
	static final int KEY_WEIGHT = 3;
	public static final char[] list = {'a','b','c','d','e','f','g','h','i','j',
			'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public static final char[] list2 = {'A','B','C','D','E','F','G','H','I','J',
			'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private static int l1 = 0;
	private static int l2 = 0;
	private static int l3 = 0;
	private static int reciprocater = 0;
	private static Pair router1;
	private static Pair router2;
	private static Pair router3;
	private static Pair router4;//反転ロータ
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length != 1 || args[0].length() != KEY_WEIGHT) {
			System.out.println("Enter key(three characters) as an argument.");
			return;
		}
		String key = args[0];
		//System.out.println(key);
		int[] num = stringToNum(key);
		//System.out.println("0:"+num[0]+" 1:"+num[1]+" 2:"+num[2]);
		l1=num[0]+1;
		l2=num[1]+1;
		l3=num[2]+1;
		router1= new Pair(l1);
		router2= new Pair(l2);
		router3= new Pair(l3);
		router4= new Pair((l1+l2+l3)%ALP_SUM+1);
//		router1.show();
//		router2.show();
//		router3.show();
		boolean bool = true;
		BufferedReader br;
		String s1;
		while(bool) {
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter string.");
			s1 = "";
			try {
				s1 = br.readLine();
			}catch(IOException e){
				System.out.println("IOExeption occurred.");
				return;
			}
			int[] s1Num = stringToNum(s1);
//			int processedNum[] = new int[s1Num.length];
			for(int l=0;l<s1.length();l++) {
//				processedNum[l]=converter(s1Num[l]);
				for(int m=0;m<ALP_SUM;m++){
					int n =converter(s1Num[l]);
					if(Integer.valueOf(n).equals(-1)) {
						System.out.print(" ");
						break;
					}else {
						System.out.print(list[n]);
						break;
					}
				}
			}
			System.out.println("\nContinue? y/n");
			br = new BufferedReader(new InputStreamReader(System.in));
			s1 = "";
			try {
				s1 = br.readLine();
			}catch(IOException e){
				System.out.println("IOExeption occurred.");
				return;
			}
			if(String.valueOf(s1).equals("y")) {
			}else {
				bool = false;
			}
		}
		
		return;
	}
	public static int[] stringToNum(String s) {
		int[] i = new int[s.length()];
		for(int j=0;j<s.length();j++) {
//			if(s.charAt(j)=='_') {
//				i[j]=-1;
//				continue;
//			}
			i[j]=-1;
			for(int k=0;k<ALP_SUM;k++) {
				if(s.charAt(j)==list[k]) {
					i[j]=k;
					break;
				}
				if(s.charAt(j)==list2[k]) {
					i[j]=k;
					break;
				}
			}
		}
		return i;
	}

	public static int converter(int s1num) {
		//例　10 -> 1 かつ　1 -> 10になるような処理
		int result = router1.getPairNum(router2.getPairNum(router3.getPairNum(router4.getPairNum(router3.getPairNum(router2.getPairNum(router1.getPairNum(s1num)))))));
		switch(reciprocater){
		case 0:
			if(l1<ALP_SUM) {
				l1++;
			}else {
				l1=1;
			}
			router1=new Pair(l1);
//			router1.show();
			reciprocater++;
			break;
		case 1:
			if(l2<ALP_SUM) {
				l2++;
			}else {
				l2=1;
			}
			router2=new Pair(l2);
//			router2.show();
			reciprocater++;
			break;
		case 2:
			if(l3<ALP_SUM) {
				l3++;
			}else {
				l3=1;
			}
			router3=new Pair(l3);
//			router3.show();
			reciprocater=0;
			break;
		}
		return result;
	}
}
class Pair{
	static final int ALP_SUM = 26;
	static final int ALP_HALF = 13;
	private int cList1[]= new int[13];
	private int cList2[]= new int[13];
	Pair(int i){
		if(i>ALP_HALF) {
			i-=ALP_HALF;
		}
		int block = ALP_HALF/i;
		int rest = ALP_HALF%i;
		int cList1Index = 0;
		int cList2Index = 0;
		for(int j=0;j<ALP_SUM;j++) {
			if(j<block*i*2) {
				if(j==0 || evenOdd(j/i)) {
					cList1[cList1Index]=j;
					cList1Index++;
				}else {
					cList2[cList2Index]=j;
					cList2Index++;
				}
			}else {
				for(int k=0;k<rest;k++) {
					cList1[cList1Index]=j;
					cList1Index++;
					j++;
				}
				for(int k=0;k<rest;k++) {
					cList2[cList2Index]=j;
					cList2Index++;
					j++;
				}
			}
		}
	}
	void show() {
		System.out.print("cList1:");
		for(int i=0;i<13;i++) {
			System.out.print(cList1[i]+" ");
		}
		System.out.println("");
		System.out.print("cList2:");
		for(int i=0;i<13;i++) {
			System.out.print(cList2[i]+" ");
		}
		System.out.println("");
	}
	int getPairNum(int i) {
		for(int j=0;j<ALP_HALF;j++) {
			if(Integer.valueOf(cList1[j]).equals(i)) {
				return cList2[j];
			}
			if(Integer.valueOf(cList2[j]).equals(i)) {
				return cList1[j];
			}
		}
		return -1;
	}
	boolean evenOdd(int i) {
		boolean bool;
		if(i==0 || i%2==0) {
			bool = true;
		}else {
			bool = false;
		}
//		System.out.println("evenOdd:"+i+bool);
		return bool;
	}
}
