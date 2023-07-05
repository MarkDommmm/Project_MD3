package run;

import Bank.ATM;
import Bank.BankSystem;
import Config.InputMethods;
import Database.IOFile;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        BankSystem bankSystem = new BankSystem();
        IOFile<ATM> atmioFile = new IOFile<>();
        List<ATM> atmList1 = atmioFile.readFromFile(IOFile.LISTATM_FILE);


        while (true) {
            if (atmList1.size() == 0) {
                System.err.println("╔═════════════════════════════╗");
                System.err.println("║  Không có ATM nào ở đây!!!  ║");
                System.err.println("╚═════════════════════════════╝");

                return;
            }
            System.out.println("╔═══════════════════════════════════════════╗");
            System.out.println("║       Danh sách ATM mời bạn chọn!!!       ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            for (int i = 0; i < atmList1.size(); i++) {
                System.out.println("║             " + (i + 1) + ". " + atmList1.get(i).getAtmID() + "                     ║");

            }
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║    Mời bạn chọn nhập ATM muốn sử dụng.    ║");
            System.out.println("╚═══════════════════════════════════════════╝");
            String choice = InputMethods.getString();

            for (int i = 0; i < atmList1.size(); i++) {
                if (choice.toLowerCase().equals(atmList1.get(i).getAtmID().toLowerCase())) {
                    atmList1.get(i).login();
                    atmList1.get(i).performTransaction();
                    break;
                }
            }

        }

    }
}
