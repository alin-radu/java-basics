package exercices;

public class NumberOfDaysInMonth {
    public static void main(String[] args) {

    }
    public static boolean isLeapYear(int year){
        if(year < 1 || year > 9999){
            return false;
        }
        if((year % 4 == 0 && year % 100 != 0) || (year % 4 == 0 && year % 100 == 0 && year % 400 == 0)){
            return true;
        }
        return false;
    }

    public static int getDaysInMonth(int month, int year){
        if(month < 1 || month > 12 || year < 1 || year > 9999){
            return -1;
        }

        int result = 0;
        switch (month){
            case 4: case 6: case 9: case 11:
                result =  30;
                break;
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                result = 31;
                break;
            case 2:
                if(isLeapYear(year)){
                    result = 29;
                }else{
                    result = 28;
                }
                break;
        }

        return result;
    }
}
