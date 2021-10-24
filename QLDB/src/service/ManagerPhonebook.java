package service;

import model.Phonebook;
import unit.ReadAndWrite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerPhonebook {
    private ArrayList<Phonebook> listPhonebook;
    public static String LINK_SAVE = "src/data/contacts.csv";
    public static Scanner input = new Scanner(System.in);
    public static final String LINK_REGEX = "(^([CDEF][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";
    public static final String NUMBER_PHONE_REGEX = "^[0][1-9][0-9]{8,9}$";
    public static final String EMAIL_REGEX = "^\\w+@\\w+(\\.\\w+){1,2}$";
    public static final String FULL_NAME_REGEX = "^([A-Z]+[a-z]*?[ ]?)+$";
    public static final String DATE_OF_BIRTH_REGEX = "^[0|1|2|3]?[0-9][/][0-1]?[0-9][/][1|2]\\d{3}$";


    public ManagerPhonebook() {
        listPhonebook = ReadAndWrite.readFileToCsv(LINK_SAVE);
    }

    public void menuManagerPhonebook() {
        char choice = ' ';
        do {
            System.out.println("-----------------------------------------------------");
            System.out.println("|             CHUONG TRINH QUAN LY DANH BA           |");
            System.out.println("-----------------------------------------------------");
            System.out.println("| Chọn chức năng theo số để tiếp tục                 |");
            System.out.println("|  1. Xem danh bạ                                    |");
            System.out.println("|  2. Thêm mới                                       |");
            System.out.println("|  3. Cập nhật                                       |");
            System.out.println("|  4. Xóa                                            |");
            System.out.println("|  5. Tìm kiếm                                       |");
            System.out.println("|  6. Đọc từ file                                    |");
            System.out.println("|  7. Ghi vào file                                   |");
            System.out.println("|                                        8. Thoát    |");
            System.out.println("------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }

            switch (choice) {
                case '1':
                    displayList(listPhonebook);
                    break;
                case '2':
                    addPhonebook();
                    break;
                case '3':
                    editPhonebook();
                    break;
                case '4':
                    deletePhonebook();
                    break;
                case '5':
                    searchPhonebook();
                    break;
                case '6':
                    importFileCSV();
                    break;
                case '7':
                    exportFileCSV();
                    break;
                case '0':
                    System.out.println("Thank !");
                    System.out.println("Bye bye.......");
                    System.exit(0);
                default:
                    System.out.println("Chọn theo menu !");
            }
        } while (choice != '0');
    }

    public void addPhonebook() {
        String numPhone, fullName, group, gender, dateOfBirth, address, email;
        do {
            System.out.println("Nhap so dien thoai");
            numPhone = input.nextLine();
            if (!isNumberPhone(numPhone)) {
                System.out.println("Khong phai sdt");
            }else if(isNumPhoneNotHaveInPhoneBook(numPhone)){
                System.out.println("So dt da co trong danh ba hay nhap lai ");
            }
        } while (!isNumberPhone(numPhone)||isNumPhoneNotHaveInPhoneBook(numPhone) );

        System.out.println("Nhap ten nhom");
        group = input.nextLine();

        do {
            System.out.println("Nhap ten");
            fullName = input.nextLine();
            if (!isFullName(fullName)) {
                System.out.println("khong phai ten dung");
            }
        } while (!isFullName(fullName));

        char choice = ' ';
        boolean isChoice = false;
        gender = "";
        do {
            isChoice = false;
            System.out.println("-----------------------");
            System.out.println("|  Gioi tinh          |");
            System.out.println("|  1.Nam              |");
            System.out.println("|  2.Nu               |");
            System.out.println("-----------------------");
            System.out.println();
            System.out.print("chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    gender = "Nam";
                    break;
                case '2':
                    gender = "Nu";
                    break;
                default:
                    isChoice = true;
            }
        } while (isChoice);

        do {
            System.out.print("Nhập ngày tháng năm sinh  có dạng day/month/year, ví dụ 2/2/1992 : ");
            dateOfBirth = input.nextLine();
            if (!isFormatDateOfBirth(dateOfBirth)) {
                System.out.println("Không phải định dạng đúng !");
            } else if (!isDateOfBirth(dateOfBirth)) {
                System.out.println("Không phải ngày thực tế hoặc đã vượt ra khỏi nằm ngoài độ tuổi cho phép ! ");
            }
        } while (!isDateOfBirth(dateOfBirth));

        System.out.print("Nhập địa chỉ chu nhan so dien thoai : ");
        address = input.nextLine();

        do {
            System.out.print("nhap dia chi email : ");
            email = input.nextLine();
            if (!isEmail(email)) {
                System.out.println("Khong ton tai dia chi email nay, hay nhajp lai ");
            }
        } while (!isEmail(email));

        Phonebook phonebook = new Phonebook(numPhone, group, fullName, gender, address, dateOfBirth, email);

        System.out.println("So dien thoai them vao danh ba la ");
        System.out.println(phonebook);

        char check = ' ';
        boolean isCheck = true;
        do {
            System.out.println("-------------------------------------------");
            System.out.println("|  Bạn có muốn lưu vào dữ liệu ?           |");
            System.out.println("|     1. Yes                               |");
            System.out.println("|     0. No                                |");
            System.out.println("-------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                check = input.nextLine().charAt(0);
            } catch (Exception e) {
                check = ' ';
            }
            switch (check) {
                case '1':
                    listPhonebook.add(phonebook);
                    ReadAndWrite.writeFileToCSV(LINK_SAVE, listPhonebook);
                    isCheck = false;
                    menuManagerPhonebook();
                    break;
                case '0':
                    menuManagerPhonebook();
                    isCheck = false;
                    break;
                default:
            }
        } while (isCheck);
    }

    public void editPhonebook() {
        String numPhone = "";
        String fullName, group, gender, dateOfBirth, address, email;

        do {
            System.out.println("Nhap sdt can thay doi thong tin ");
            numPhone = input.nextLine();
            if (!isNumberPhone(numPhone)) {
                System.out.println("So dien thoai khong ton tai, hay nhap lai ");
                menuManagerPhonebook();
            } else if (!isNumPhoneNotHaveInPhoneBook(numPhone)) {
                System.out.println("so dt khong co trong danh sach ");
            }
        } while (!isNumberPhone(numPhone) || !isNumPhoneNotHaveInPhoneBook(numPhone));

        for(Phonebook pb : listPhonebook){
           if( pb.getNumberPhone().equals(numPhone)){
               System.out.println("So dien thoai ban muon thay doi co thong tin nhu sau ");
               System.out.println(pb);
               break;
           }
        }

        System.out.println("Nhap ten nhom muon thay doi ");
        group = input.nextLine();

        do {
            System.out.println("Nhap ten nguoi dung moi  ");
            fullName = input.nextLine();
            if (!isFullName(fullName)) {
                System.out.println("khong phai ten dung");
            }
        } while (!isFullName(fullName));

        char choice = ' ';
        boolean isChoice = false;
        gender = "";
        do {
            isChoice = false;
            System.out.println("-----------------------");
            System.out.println("|  Gioi tinh          |");
            System.out.println("|  1.Nam              |");
            System.out.println("|  2.Nu               |");
            System.out.println("-----------------------");
            System.out.println();
            System.out.print("chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    gender = "Nam";
                    break;
                case '2':
                    gender = "Nu";
                    break;
                default:
                    isChoice = true;
            }
        } while (isChoice);

        do {
            System.out.print("Nhập ngày tháng năm sinh  có dạng day/month/year, ví dụ 2/2/1992 : ");
            dateOfBirth = input.nextLine();
            if (!isFormatDateOfBirth(dateOfBirth)) {
                System.out.println("Không phải định dạng đúng !");
            } else if (!isDateOfBirth(dateOfBirth)) {
                System.out.println("Không phải ngày thực tế hoặc đã vượt ra khỏi nằm ngoài độ tuổi cho phép ! ");
            }
        } while (!isDateOfBirth(dateOfBirth));

        System.out.print("Nhập địa chỉ chu nhan so dien thoai : ");
        address = input.nextLine();

        do {
            System.out.print("nhap dia chi email  : ");
            email = input.nextLine();
            if (!isEmail(email)) {
                System.out.println("Khong ton tai dia chi email nay, hay nhap lai ");
            }
        } while (!isEmail(email));

        Phonebook phonebook = new Phonebook(numPhone, group, fullName, gender, dateOfBirth, address, email);

        System.out.println("So dien thoai them vao danh ba la ");
        System.out.println(phonebook);



        char check = ' ';
        boolean isCheck = true;
        do {
            System.out.println("-------------------------------------------");
            System.out.println("|  Bạn có muốn lưu thay doi vào dữ liệu ?  |");
            System.out.println("|     1. Yes                               |");
            System.out.println("|     0. No                                |");
            System.out.println("-------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                check = input.nextLine().charAt(0);
            } catch (Exception e) {
                check = ' ';
            }
            switch (check) {
                case '1':
                    for(Phonebook pb : listPhonebook){
                        if( pb.getNumberPhone().equals(numPhone)){
                            pb.setFullname(fullName);
                            pb.setGender(gender);
                            pb.setAddress(address);
                            pb.setDateOfBirth(dateOfBirth);
                            pb.setEmail(email) ;
                            break;
                        }
                    }
                    listPhonebook.add(phonebook);
                    ReadAndWrite.writeFileToCSV(LINK_SAVE, listPhonebook);
                    isCheck = false;
                    menuManagerPhonebook();
                    break;
                case '0':
                    menuManagerPhonebook();
                    isCheck = false;
                    break;
                default:
            }
        } while (isCheck);
    }


    public void displayList(ArrayList<Phonebook> listPhonebook) {
        System.out.println("Danh bạ : ");
        System.out.println();
        System.out.printf("%-7s%-20s%-10s%-20S%-10s%s\n", "STT", "NUMBERPHONE", "GROUP", "FULLNAME", "GENDER", "ADDRESS");
        int count = 0;
        for (Phonebook phonebook : listPhonebook) {
            count++;
            System.out.printf("%-7s%-20s%-10s%-20S%-10s%s\n", String.valueOf(count), phonebook.getNumberPhone(), phonebook.getGroup(), phonebook.getFullname(), phonebook.getGender(), phonebook.getAddress());
        }
        ;
        System.out.println();
    }

    public void searchPhonebook() {
        int count = 0;
        System.out.print("Nhập số điện thoại cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        ArrayList<Phonebook> listPhonebookSearch = new ArrayList<>();
        for (Phonebook phonebook : listPhonebook) {
            if (phonebook.getNumberPhone().toLowerCase().contains(search)) {
                listPhonebookSearch.add(phonebook);
            }
        }
        if (listPhonebookSearch.isEmpty()) {
            System.out.println("Khong tim thay so dien thoia nay trong danh ba");
        } else {
            displayList(listPhonebookSearch);
        }
        menuManagerPhonebook();
    }

    public void deletePhonebook() {
        System.out.println("Nhập số điện thoai muốn xóa");
        String numPhone = input.nextLine();
        Phonebook pb = new Phonebook();
        for (Phonebook phonebook : listPhonebook) {
            if (phonebook.getNumberPhone().equals(numPhone)) {
                pb = phonebook;
                break;
            }
        }
        if (pb == null) {
            System.out.println("Khong timn thay so dien thoai trong danh ba");
        } else {
            char choice = ' ';
            boolean isChoice = false;
            do {
                isChoice = false;
                System.out.println("Ban muon xoa , 'Y' để xóa, 'N' để huy bo; ");
                try {
                    choice = input.nextLine().charAt(0);
                } catch (Exception e) {
                    choice = ' ';
                }
                switch (choice) {
                    case 'y':
                    case 'Y':
                        listPhonebook.remove(pb);
                        break;
                    case 'n':
                    case 'N':
                        break;
                    default:
                        isChoice = true;
                }
                ;
            } while (isChoice);

        }
        menuManagerPhonebook();
    }

    ;


    public void exportFileCSV() {
        String link = "";
        String nameFileCsv = "";
        String linkFull = "";
        boolean isChoice = true;
        do {
            isChoice = false;
            do {
                System.out.print("Nhập đường dẫn file xuất ra : ");
                link = input.nextLine();
                if (!isLink(link)) {
                    System.out.println("Định dạng đường dẫn không đúng ! ví dụ đường dẫn file : D:\\nameFoder\\....");
                }
            }
            while (!isLink(link));

            System.out.print("Nhập tên file : ");
            nameFileCsv = input.nextLine();
            linkFull = link + "\\" + nameFileCsv + ".csv";

            //tao foder
            String[] arr = link.split("\\\\");
            int i = 0;
            String l = "";
            while (i < arr.length) {
                l = l + arr[i] + "\\";
                File dir = new File(l);
                dir.mkdir();
                i++;
            }

            File file = new File(linkFull);
            if (!file.exists()) {
                ReadAndWrite.writeFileToCSV(linkFull, listPhonebook);
                System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                System.out.println();
                menuManagerPhonebook();
            } else {
                char press = ' ';
                boolean isPress = true;
                do {
                    System.out.println("----------------------------------------------");
                    System.out.println("|   File đã tồn tại ! Bạn có muốn ghi đè !     |");
                    System.out.println("|      1. Thực hiện ghi đè                     |");
                    System.out.println("|      2. Thay đổi đường dẫn                   |");
                    System.out.println("|      0. Quay lại                             |");
                    System.out.println("-----------------------------------------------");
                    System.out.println();
                    System.out.print("Chọn : ");
                    try {
                        press = input.nextLine().charAt(0);
                    } catch (Exception e) {
                        press = ' ';
                    }
                    switch (press) {
                        case '1':
                            ReadAndWrite.writeFileToCSV(linkFull, listPhonebook);
                            System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                            isChoice = false;
                            isPress = false;
                            menuManagerPhonebook();
                            break;
                        case '2':
                            exportFileCSV();
                            isPress = false;
                            break;
                        case '0':
                            isChoice = false;
                            isPress = false;
                            menuManagerPhonebook();
                        default:
                    }
                } while (isPress);
            }

        } while (isChoice);
    }

    public boolean isLink(String strLinks) {
        Pattern pattern = Pattern.compile(LINK_REGEX);
        Matcher matcher = pattern.matcher(strLinks);
        return matcher.matches();
    }

    public void importFileCSV() {
        String linkFolder;
        do {
            System.out.print("Nhập đường dẫn folder chứa dữ liệu cũ : ");
            linkFolder = input.nextLine();
            if (!isLink(linkFolder)) {
                System.out.println("Định dạng đường dẫn không đúng ! ví dụ đường dẫn file : D:\\nameFoder\\....");
                menuManagerPhonebook();
            }
        }
        while (!isLink(linkFolder));
        char choice = ' ';
        boolean isChoice = false;
        do {
            isChoice = false;
            System.out.print("Bạn thực sự muốn đọc dữ liệu từ folder " + linkFolder + "cho hệ thống");
            System.out.println("Mọi thông số dữ liệu có sẵn sẽ thay đổi, bạn có muốn thực hiện điều đó !");
            System.out.println("------------------");
            System.out.println("|  1. Yes         |");
            System.out.println("|  0. No          |");
            System.out.println("-------------------");
            System.out.println();
            System.out.print("Chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    File sourceFolder = new File(linkFolder);
                    File targetFolder = new File(LINK_SAVE);
                    try {
                        ReadAndWrite.copyFolder(sourceFolder, targetFolder);
                        System.out.println("Đọc dữ liệu thành công ! Hãy kiểm tra lại dữ liệu của bạn !");
                    } catch (IOException e) {
                        System.out.println("Khôi phục data lỗi ! Hãy thử lại !");
                    }
                    menuManagerPhonebook();
                    break;
                case '0':
                    menuManagerPhonebook();
                    break;
                default:
                    System.out.println("Hãy lựa chọn cẩn thận vì nó sẽ làm thay đổi các dữ liệu của hệ thống đã có !");
                    isChoice = true;
            }
        } while (isChoice);
    }

    public boolean isNumberPhone(String strNum) {
        return Pattern.compile(NUMBER_PHONE_REGEX).matcher(strNum).matches();
    }

    public boolean isEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public boolean isFullName(String fullName) {
        return Pattern.compile(FULL_NAME_REGEX).matcher(fullName).matches();
    }

    public boolean isFormatDateOfBirth(String date) {
        return Pattern.compile(DATE_OF_BIRTH_REGEX).matcher(date).matches();
    }

    public boolean isDateOfBirth(String dateOfBirth) {
        if (!isFormatDateOfBirth(dateOfBirth)) return false;
        String[] arr = dateOfBirth.split("/");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);
        if (!isYearOfBirth(year)) return false;
        if (!isMonth(month)) return false;
        boolean isDay = false;
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                if (day > 0 && day <= 30) isDay = true;
                break;
            case 2:
                if (isLeapYear(year)) {
                    if (day > 0 && day <= 29) isDay = true;
                } else if (day > 0 && day <= 28) isDay = true;
                break;
            default:
                if (day > 0 && day <= 31) isDay = true;
        }
        return isDay;
    }

    private boolean isYearOfBirth(int year) {
        if (year >= 1900 && year < 2022) return true;
        return false;
    }

    private boolean isMonth(int month) {
        if (month >= 1 && month <= 12) return true;
        return false;
    }

    private boolean isLeapYear(int year) {
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) return true;
        return false;
    }

    private boolean isNumPhoneNotHaveInPhoneBook(String numPhone) {
        if (!isNumberPhone(numPhone)) return false;
        for (Phonebook pb : listPhonebook) {
            if (pb.getNumberPhone().equals(numPhone)) {
                return true;
            }
        }
        return false;
    }

}




