import java.util.*;

class Account
{
    String name,phoneNumber,dateOfBirth,email;
    int age;
    Account(String name, String phoneNumber, String dateOfBirth, String email, int age)
    {
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.dateOfBirth=dateOfBirth;
        this.email=email;
        this.age=age;
    }
}

class Savings extends Account
{
    String accountNumber,history;
    int pin;
    double balance;
    Savings(String name, String phoneNumber, String dateOfBirth, String email, int age, String accountNumber, int pin, double balance)
    {
        super(name, phoneNumber, dateOfBirth, email, age);
        this.accountNumber=accountNumber;
        this.pin=pin;
        this.balance=balance;
        this.history="Savings account created with balance "+Double.toString(balance)+" rupees";
    }
    void withdraw(double amount)
    {
        if(balance>amount)
        {
            balance-=amount;
            history+="\n"+Double.toString(amount)+" withdrawn. Current balance = "+Double.toString(balance)+" rupees";
        }
        else
        System.out.println("Insufficient balance. Cannot withdraw the required amount");
    }
    void deposit(double amount)
    {
        balance+=amount;
        history+="\n"+Double.toString(amount)+" deposited. Current balance = "+Double.toString(balance)+" rupees";
    }
    void balanceInquiry()
    {
        System.out.println("Current balance = "+balance);
    }
    void transactionHistory()
    {
        System.out.println(history);
    }
}

class Current extends Account
{
    String accountNumber,history;
    int pin;
    double balance,overdraftLimit;
    Current(String name, String phoneNumber, String dateOfBirth, String email, int age, String accountNumber, int pin, double balance,double overdraftLimit)
    {
        super(name, phoneNumber, dateOfBirth, email, age);
        this.accountNumber=accountNumber;
        this.pin=pin;
        this.balance=balance;
        this.overdraftLimit=0-overdraftLimit;
        this.history="Current account created with balance "+Double.toString(balance)+" rupees and overdraft limit of "+Double.toString(overdraftLimit)+" rupees";
    }
    int checkBalance(double amount)
    {
        if(balance<overdraftLimit)
        {
            System.out.println("Overdraft limit exceeded. Cannot withdraw money due to insufficient funds");
            return 0;
        }
        if((balance-amount)<overdraftLimit)
        {
            System.out.println("Overdraft limit will be exceeded on withdrawal. Cannot withdraw money due to insufficient funds");
            return 0;
        }
        return 1;
    }
    void withdraw(double amount)
    {
        if(checkBalance(amount)==1)
        {
            balance-=amount;
            history+="\n"+Double.toString(amount)+" withdrawn. Current balance = "+Double.toString(balance)+" rupees";
        }
    }
    void deposit(double amount)
    {
        balance+=amount;
        history+="\n"+Double.toString(amount)+" deposited. Current balance = "+Double.toString(balance)+" rupees";
    }
    void balanceInquiry()
    {
        System.out.println("Current balance = "+balance);
    }
    void transactionHistory()
    {
        System.out.println(history);
    }
}

class bankSystem
{
    private int checkAge(int age)
    {
        if(age<18)
        return 0;
        return 1;
    }
    private int checkPhoneNumber(String phoneNumber)
    {
        if(phoneNumber.length()!=10)
        return 0;
        return 1;
    }
    private int checkDateOfBirth(String dateOfBirth)
    {
        String[] days=dateOfBirth.split("/");
        int d=Integer.parseInt(days[0]);
        int m=Integer.parseInt(days[1]);
        int y=Integer.parseInt(days[2]);
        if((d>=1 && d<=31 && (m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12)) || (d>=1 && d<=30 &&(m==4 || m==6 || m==9 || m==11))||(d>=1 && d<=29 && m==2 && ((y%4==0 && y%100!=0)||y%400==0))||(d>=1 && d<=28))
        {
            if(y<=2006)
            return 1;
        }
        return 0;
    }
    private int checkEmail(String email)
    {
        int idx1,idx2;
        idx1=email.indexOf('@');
        idx2=email.lastIndexOf('@');
        if(idx1!=-1 && idx1==idx2 && email.endsWith(".com"))
        return 1;
        return 0;
    }
    String checkValidity(String name, String phoneNumber, String dateOfBirth, String email, int age)
    {
        int a=checkAge(age);
        int ph=checkPhoneNumber(phoneNumber);
        int dob=checkDateOfBirth(dateOfBirth);
        int em=checkEmail(email);
        String res="";
        if(a==0)
        res+="You must be atleast 18 years of age";
        if(ph==0)
        res+="\nYou have entered an invalid phone number";
        if(dob==0)
        res+="\nYou have entered an invalid date of birth";
        if(em==0)
        res+="\nYou have entered an invalid email address";
        if(res=="")
        res="Personal details validated";
        return res;
    }
    int checkAccountValidity(String name, String phoneNumber, String dateOfBirth, String email, int age, String accountNumber, double balance)
    {
        String res;
        int a=0,b=0;
        res=checkValidity(name, phoneNumber, dateOfBirth, email, age);
        if(accountNumber.length()!=13)
        {
            res+="\nYou have entered an invalid account number";
            a=1;
        }
        if(balance<1000)
        {
            res+="\nYour balance must be atleast Rs. 1000 to create an account";
            b=1;
        }
        if(a==0 && b==0)
        res+="\nAccount details validated";
        System.out.println(res);
        if(res.equals("Personal details validated\nAccount details validated"))
        return 1;
        else
        return 0;
    }
    int findSavings(List<Savings> sav,String acc)
    {
        int i,n=sav.size();
        for(i=0;i<n;i++)
        {
            if(sav.get(i).accountNumber.equals(acc))
            return i;
        }
        return -1;
    }
    int findCurrent(List<Current> cur,String acc)
    {
        int i,n=cur.size();
        for(i=0;i<n;i++)
        {
            if(cur.get(i).accountNumber.equals(acc))
            return i;
        }
        return -1;
    }
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        Scanner sc1=new Scanner(System.in);
        Scanner sc2=new Scanner(System.in);
        System.out.println("\tWelcome to the BANKING SYSTEM\n");
        System.out.println("Certain guidelines before you begin : \n1.You must be atleast 18 years of age to create an account\n2.while entering your DOB, enter it in dd/mm/yyyy format like 12/01/2004\n3.Your account must have atleast 1000 rupees at the time of creation");
        System.out.print("Enter 1 to continue : ");
        int f=sc.nextInt();
        bankSystem ob=new bankSystem();
        List<Savings> savingAccount=new ArrayList<>();
        List<Current> currentAccount=new ArrayList<>();
        while(f==1)
        {
            System.out.println("\t1.Creating an account\n\t2.Withdrawing money\n\t3.Depositing money\n\t4.Balance inquiry\n\t5.Obtain transaction history");
            System.out.println("Enter your choice : ");
            int ch=sc.nextInt();
            switch(ch)
            {
                case 1:
                System.out.print("Enter your name : ");
                String name=sc1.nextLine();
                System.out.print("Enter your phone number : ");
                String phoneNumber=sc1.nextLine();
                System.out.print("Enter your date of birth : ");
                String dateOfBirth=sc1.nextLine();
                System.out.print("Enter your email address : ");
                String email=sc1.nextLine();
                System.out.print("Enter your age : ");
                int age=sc1.nextInt();
                System.out.print("Enter S to create a Savings account and C to create a Current account : ");
                char chr=sc1.next().charAt(0);
                if(chr=='S'||chr=='s')
                {
                    System.out.print("Enter your account number : ");
                    String acc_no=sc2.nextLine();
                    System.out.print("Enter your balance : ");
                    double b=sc1.nextDouble();
                    int res=ob.checkAccountValidity(name, phoneNumber, dateOfBirth, email, age, acc_no, b);
                    if(res==0)
                    {
                        System.out.println("Account creation failed! Please try again.");
                        break;
                    }
                    System.out.println("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    Savings acc=new Savings(name, phoneNumber, dateOfBirth, email, age, acc_no, pin, b);
                    savingAccount.add(acc);
                    System.out.println("Account created successfully.");
                    break;
                }
                if(chr=='C'||chr=='c')
                {
                    System.out.print("Enter your account number : ");
                    String acc_no=sc2.nextLine();
                    System.out.print("Enter your balance : ");
                    double b=sc1.nextDouble();
                    int res=ob.checkAccountValidity(name, phoneNumber, dateOfBirth, email, age, acc_no, b);
                    if(res==0)
                    {
                        System.out.println("Account creation failed! Please try again.");
                        break;
                    }
                    System.out.println("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    System.out.println("Enter your overdraft limit : ");
                    double ovl=sc1.nextDouble();
                    Current acc=new Current(name, phoneNumber, dateOfBirth, email, age, acc_no, pin, b, ovl);
                    currentAccount.add(acc);
                    System.out.println("Account created successfully.");
                    break;
                }
                break;
                case 2:
                System.out.print("Enter S for Savings or C for Current account : ");
                char type=sc1.next().charAt(0);
                System.out.print("Enter your account number : ");
                String acc=sc2.nextLine();
                if(type=='S')
                {
                    int found=ob.findSavings(savingAccount, acc);
                    if(found==-1)
                    {
                        System.out.println("No such account found.");
                        break;
                    }
                    Savings ob1=savingAccount.get(found);
                    System.out.print("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    if(pin!=ob1.pin)
                    {
                        System.out.println("You have entered the wrong PIN. Transaction ended.");
                        break;
                    }
                    System.out.print("Enter the amount you want to withdraw : ");
                    double amount=sc1.nextDouble();
                    ob1.withdraw(amount);
                    System.out.println("Transaction ended.");
                    break;
                }
                if(type=='C')
                {
                    int found=ob.findCurrent(currentAccount, acc);
                    if(found==-1)
                    {
                        System.out.println("No such account found.");
                        break;
                    }
                    Current ob1=currentAccount.get(found);
                    System.out.print("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    if(pin!=ob1.pin)
                    {
                        System.out.println("You have entered the wrong PIN. Transaction ended.");
                        break;
                    }
                    System.out.print("Enter the amount you want to withdraw : ");
                    double amount=sc1.nextDouble();
                    ob1.withdraw(amount);
                    System.out.println("Transaction ended.");
                    break;
                }
                break;
                case 3:
                System.out.print("Enter S for Savings or C for Current account : ");
                char t=sc1.next().charAt(0);
                System.out.print("Enter your account number : ");
                String acn=sc2.nextLine();
                if(t=='S')
                {
                    int found=ob.findSavings(savingAccount, acn);
                    if(found==-1)
                    {
                        System.out.println("No such account found.");
                        break;
                    }
                    Savings ob1=savingAccount.get(found);
                    System.out.print("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    if(pin!=ob1.pin)
                    {
                        System.out.println("You have entered the wrong PIN. Transaction ended.");
                        break;
                    }
                    System.out.print("Enter the amount you want to deposit : ");
                    double amount=sc1.nextDouble();
                    ob1.deposit(amount);
                    System.out.println("Transaction ended.");
                    break;
                }
                if(t=='C')
                {
                    int found=ob.findCurrent(currentAccount, acn);
                    if(found==-1)
                    {
                        System.out.println("No such account found.");
                        break;
                    }
                    Current ob1=currentAccount.get(found);
                    System.out.print("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    if(pin!=ob1.pin)
                    {
                        System.out.println("You have entered the wrong PIN. Transaction ended.");
                        break;
                    }
                    System.out.print("Enter the amount you want to deposit : ");
                    double amount=sc1.nextDouble();
                    ob1.deposit(amount);
                    System.out.println("Transaction ended.");
                    break;
                }
                break;
                case 4:
                System.out.print("Enter S for Savings or C for Current account : ");
                char tp=sc1.next().charAt(0);
                System.out.print("Enter your account number : ");
                String acno=sc2.nextLine();
                if(tp=='S')
                {
                    int found=ob.findSavings(savingAccount, acno);
                    if(found==-1)
                    {
                        System.out.println("No such account found.");
                        break;
                    }
                    Savings ob1=savingAccount.get(found);
                    System.out.print("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    if(pin!=ob1.pin)
                    {
                        System.out.println("You have entered the wrong PIN. Transaction ended.");
                        break;
                    }
                    ob1.balanceInquiry();
                    break;
                }
                if(tp=='C')
                {
                    int found=ob.findCurrent(currentAccount, acno);
                    if(found==-1)
                    {
                        System.out.println("No such account found.");
                        break;
                    }
                    Current ob1=currentAccount.get(found);
                    System.out.print("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    if(pin!=ob1.pin)
                    {
                        System.out.println("You have entered the wrong PIN. Transaction ended.");
                        break;
                    }
                    ob1.balanceInquiry();
                    break;
                }
                break;
                case 5:
                System.out.print("Enter S for Savings or C for Current account : ");
                char tpe=sc1.next().charAt(0);
                System.out.print("Enter your account number : ");
                String accno=sc2.nextLine();
                if(tpe=='S')
                {
                    int found=ob.findSavings(savingAccount, accno);
                    if(found==-1)
                    {
                        System.out.println("No such account found.");
                        break;
                    }
                    Savings ob1=savingAccount.get(found);
                    System.out.print("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    if(pin!=ob1.pin)
                    {
                        System.out.println("You have entered the wrong PIN. Transaction ended.");
                        break;
                    }
                    ob1.transactionHistory();
                    break;
                }
                if(tpe=='C')
                {
                    int found=ob.findCurrent(currentAccount, accno);
                    if(found==-1)
                    {
                        System.out.println("No such account found.");
                        break;
                    }
                    Current ob1=currentAccount.get(found);
                    System.out.print("Enter your PIN : ");
                    int pin=sc1.nextInt();
                    if(pin!=ob1.pin)
                    {
                        System.out.println("You have entered the wrong PIN. Transaction ended.");
                        break;
                    }
                    ob1.transactionHistory();
                    break;
                }
                break;
                default:
                System.out.println("Invalid option selected");
            }
            System.out.print("Enter 1 to continue : ");
            f=sc.nextInt();
        }
    }
}