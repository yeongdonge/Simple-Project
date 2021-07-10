
import java.util.Scanner;
import java.text.DecimalFormat;

class Simpletron {
    Scanner sc = new Scanner(System.in);
    // Register initialization
    int memory[];
    int accumulator = 0;
    int pointer = 0;
    int num = 0;
    int count = 0;
    int instructionCounter = 0;
    int instructionsRegister = 0;
    int operationCode = 0;
    int operand = 0;


    public Simpletron() {
        this.memory = new int[100];
    }

    public void start() {
        System.out.println("*** Welcome to Simpletron! ***");
        System.out.println("*** Please enter your program one instruction ***");
        System.out.println("*** (or data word) at a time. I will type the ***");
        System.out.println("*** location number and a question mark (?) ***");
        System.out.println("*** You then type the word for that location ***");
        System.out.println("*** Press the -99999 to stop entering  ***");
        System.out.println("*** your program ***");

        while (num != -99999 || count >= memory.length) {
            System.out.printf("%02d ? +", count);
            num = sc.nextInt();
            memory[count++] = num;
        }
        System.out.println("*** Program loading completed ***");
        System.out.println("*** Program execution begins ***");

        for (pointer = 0; pointer < count; pointer++) {
            instructionsRegister = memory[pointer];
            operationCode = instructionsRegister / 100;
            operand = instructionsRegister % 100;
            switch (operationCode) {
                case 10: // READ
                    int input = 0;
                    System.out.printf("operand%d : ", operand);
                    input = sc.nextInt();
                    memory[operand] = input;
                    if (memory[operand] < -9999 || memory[operand] > 9999) {
                        System.out.println("범위를 초과하였습니다.");
                        System.out.println("*** Simpletron execution abnomally terminated ***");
                        System.exit(1);
                    }
                    break;
                case 11: // WRITE
                    System.out.printf("출력 : %d\n", memory[operand]);
                    break;
                case 20: // LOAD
                    accumulator = memory[operand];
                    break;
                case 21: // STORE
                    memory[operand] = accumulator;
                    break;
                case 30: // ADD
                    accumulator += memory[operand];
                    break;
                case 31: // SUBTRACT
                    accumulator -= memory[operand];
                    break;
                case 32: // DIVIDE
                    if (memory[operand] == 0) {
                        System.out.println("*** Attempt to divide by zero ***");
                        System.exit(1);
                    } else accumulator /= memory[operand];
                    break;
                case 33: // MULTIPLY
                    accumulator *= memory[operand];
                    break;
                case 40: // BRANCH
                    pointer = operand - 1;
                    break;
                case 41: // BRANCHNEG
                    if (accumulator < 0) pointer = operand - 1;
                    break;
                case 42: // BRANCHZERO
                    if (accumulator == 0) pointer = operand - 1;
                    break;
                case 43: // HALT
                    System.out.println("*** Simpletron execution terminated ***\n");
                    pointer = count;
                    dump();
                    System.exit(0);
                    break;
                default:
                    System.out.println("*** 옳지 않은 OP-CODE 입니다. ***");
                    System.out.printf(" ***** !WARNING instructionRegister : %d *****\n", memory[pointer]);
                    break;
            }
        }
    }
    public void dump() {
        System.out.println("REGISTERS:");
        System.out.printf("accumulator : %05d\n", accumulator);
        System.out.printf("instructionCounter : %d\n", pointer+1);
        System.out.printf("instructionRegister : +%04d\n", memory[pointer+1]);
        System.out.printf("operationCode : %d\n", operationCode);
        System.out.printf("operand : %02d\n", operand);
        System.out.println("MEMORY:");

        for(int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
                System.out.printf("+%04d ", memory[10*i+j]);
            System.out.println();
        }
    }
}
public class SimpletronTest {
    public static void main(String[] args) {
        Simpletron t = new Simpletron();
        t.start();
    }
}
