import java.util.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class staff{
	private String account;
	private String password;
	private String time;
	public void setAccount(String account){
		this.account = account;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getAccount(){
		return account;
	}

	public String getPassword(){
		return password;
	}

	public String getTime(){
		return time;
	}
}


class homework2{
	
	public static int datalen(String fname)throws Exception	{
		int count=0;
		Scanner scn=new Scanner(new File(fname));
		while(scn.hasNextLine()){
			count++;
			System.out.println(scn.nextLine());
		}
		scn.close();
		return count-1;//忽略項目account/password
	}
	
	
	public static void dataloading(String fname,staff[] stafflist)throws Exception{
		int count=0;
		Scanner scn=new Scanner(new File(fname));
		scn.nextLine();
		while(scn.hasNextLine()){
		stafflist[count].setAccount(scn.next());
		stafflist[count++].setPassword(scn.next());
		}
		scn.close();
	}
	
	public static void clientin(staff client,staff[] stafflist,int flen,Scanner scn){
		System.out.println("帳號:");
		client.setAccount(scn.nextLine());
		System.out.println("密碼:");
		client.setPassword(scn.nextLine());
		boolean sw=false;
		for (int i=0;i<flen;i++)
			if (client.getAccount().equals(stafflist[i].getAccount()) && client.getPassword().equals(stafflist[i].getPassword())) {
				sw=true;
				LocalDateTime nowtime=LocalDateTime.now();
				stafflist[i].setTime(nowtime.format(DateTimeFormatter.ofPattern("HH:mm")));
				System.out.println(client.getAccount()+",最近簽到時間:"+stafflist[i].getTime());
				break;
			}
		if(sw==false)
			System.out.println("帳號/密碼不符,不要代簽");
}

	public static void printlist(staff[] stafflist){
		System.out.println("簽到帳號/簽到時間");
		for(staff i:stafflist)
			if(i.getTime()!=null)
				System.out.println(i.getAccount()+"/"+i.getTime());
	}

	public static void pwchange(Scanner scn,staff[] stafflist, int flen){
		System.out.println("請輸入要變更密碼的帳號:");
		String temp=scn.nextLine();
		for(int i=0;i<flen;i++)
			if(stafflist[i].getAccount().equals(temp)){
				System.out.println("請輸入新密碼:");
				stafflist[i].setPassword(scn.nextLine());
				System.out.println("密碼變更完成,新密碼為:"+stafflist[i].getPassword());
				break;
			}
	}
	
	public static void main (String[] argv)throws Exception{
		staff client=new staff();
		String fname=argv[0];
		int flen=datalen(fname);//整理資料長度
		
		staff[]stafflist=new staff[flen];//帳號清單
		for(int i=0;i<flen;i++)
			stafflist[i]=new staff();
			//不能使用fill,會指向同一個address
			
		dataloading(fname,stafflist);//資料輸入
		
		int select=1;
		Scanner scn=new Scanner(System.in);
		while(select>0){
			System.out.println("請選擇功能\n1.帳號簽到\n2.簽到表顯示\n3.變更密碼\n0.離開");
			select=Integer.parseInt(scn.nextLine());
			switch(select){
			case 1:
				clientin(client,stafflist,flen,scn);
				break;
			case 2:
				printlist(stafflist);
				break;
			case 3:
				pwchange(scn,stafflist,flen);
				break;
			}
		}
		scn.close();
	}	
}