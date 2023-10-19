import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class HackAssembler {
    public static void main(String[] args) {
        if(args.length == 2) {
            String inputFile = args[0];
            String outputFile = args[1];
            processInputFile(inputFile, outputFile);
        }
        else {
            System.out.println("Usage: java HackAssembler input-file.asm output-file.hack");
        }
        
    }

    public static void processInputFile(String input, String output) {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(input));
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                writeOutputLine(convertToMachineCode(line), output);
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String convertToMachineCode(String input) {
        char instType;
        String opcode = null;
        String machineCode = null;

        // if the line is a comment
        if (isWhiteSpace(input)) {
            return null;
        }
        // if the line is an A-instruction
        else if (input.startsWith("@")) {
            instType = 'A';
        }
        // if the line is a C-instruction
        else {
            instType = 'C';
        }

        switch(instType) {
            case 'A':
                // code for converting A-type instructions
                String[] aInst = input.split("@");
                // separate the instruction into an array:
                // aInst[0] = null, aInst[1] = memory address
                int memAddress = Integer.parseInt(aInst[1]);
                String binaryAddress = Integer.toBinaryString(memAddress);
                // after converting address to binary, pad with 0s
                while(binaryAddress.length() < 16) {
                    binaryAddress = "0" + binaryAddress;
                }
                return binaryAddress;
            case 'C':
                // code for converting C-type instructions
                String[] cInst = splitString(input);
                System.out.println(cInst[0]);
                System.out.println(cInst[1]);
                System.out.println(cInst[2]);
                break;
            default:
                System.out.println("Unknown instruction type");
                System.exit(1);
        }

        return machineCode;
    }

    private static String[] splitString(String input) {
        return input.split("=" + "|" + ";");
    }

    private static boolean isWhiteSpace(String input) {
        if(input.startsWith("//")) {
            return true;
        }
        return false;
    }

    public static void writeOutputLine(String line, String output) {
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(output, true));
            if(line != null) {
                writer.write(line);
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}