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
                //System.out.println(line);
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
                // initialize an array to hold our split string
                String[] cInst = splitCInst(input);
                // cInst[0] = dest
                // cInst[1] = comp
                // cInst[2] = jump
                String dest = parseDest(cInst[0]);
                String comp = parseComp(cInst[1]);
                String jump = parseJump(cInst[2]);
                return ("111" + comp + dest + jump);
            default:
                System.out.println("Unknown instruction type");
                System.exit(1);
        }   
        return null;        
    }

    private static String[] splitCInst(String phrase) {
        String[] parts = new String[3];

        // Split the phrase using '=' as a delimiter
        String[] splitByEqual = phrase.split("=", 2);

        if (splitByEqual.length == 2) {
            // '=' found, set the first part to the part before '=', the second part to the part between '=', and ';'
            parts[0] = splitByEqual[0];
            String[] splitBySemicolon = splitByEqual[1].split(";", 2);
            parts[1] = splitBySemicolon[0];
            parts[2] = splitBySemicolon.length > 1 ? splitBySemicolon[1] : "";
        } else {
            // No '=', set the second and third parts to empty strings
            parts[0] = "";
            String[] splitBySemicolon = phrase.split(";", 2);
            parts[1] = splitBySemicolon[0];
            parts[2] = splitBySemicolon.length > 1 ? splitBySemicolon[1] : "";
        }

        return parts;
    }

    private static boolean isWhiteSpace(String input) {
        if(input.startsWith("//") || input.isEmpty()) {
            return true;
        }
        return false;
    }

    private static String parseComp(String input) {
        //System.out.println("comp = " + input);
        String aBit = null;
        String cBits = null;
        // compute aBit
        if(input.contains("M")) {
            aBit = "1";
        }
        else {
            aBit = "0";
        }

        // compute cBits
        switch(input) {
            case "0":
                cBits = "101010";
                break;
            case "1":
                cBits = "111111";
                break;
            case "-1":
                cBits = "111010";
                break;
            case "D":
                cBits = "001100";
                break;
            case "A":
            case "M":
                cBits = "110000";
                break;
            case "!D":
                cBits = "001101";
                break;
            case "!A":
            case "!M":
                cBits = "110001";
                break;
            case "-D":
                cBits = "001111";
                break;
            case "-A":
            case "-M":
                cBits = "110011";
                break;
            case "D+1":
                cBits = "011111";
                break;
            case "A+1":
            case "M+1":
                cBits = "110111";
                break;
            case "D-1":
                cBits = "001110";
                break;
            case "A-1":
            case "M-1":
                cBits = "110010";
                break;
            case "D+A":
            case "D+M":
                cBits = "000010";
                break;
            case "D-A":
            case "D-M":
                cBits = "010011";
                break;
            case "A-D":
            case "M-D":
                cBits = "000111";
                break;
            case "D&A":
            case "D&M":
                cBits = "000000";
                break;
            case "D|A":
            case "D|M":
                cBits = "010101";
                break;
            default:
                cBits = "000000";
                break;
        }  
        //System.out.println("aBit = " + aBit);
        //System.out.println("cBits = " + cBits);
        return (aBit + cBits);
        
    }

    private static String parseDest(String input) {
        //System.out.println("dest = " + input);
        String dBits = null;
        if(input == null || input == "") {
            dBits = "000";
        }
        else {
            switch(input) {
                case "M":
                    dBits = "001";
                    break;
                case "D":
                    dBits = "010";
                    break;
                case "MD":
                    dBits = "011";
                    break;
                case "A":
                    dBits = "100";
                    break;
                case "AM":
                    dBits = "101";
                    break;
                case "AD":
                    dBits = "110";
                    break;
                case "AMD":
                    dBits = "111";
                    break;
            }
        }
        return dBits;
    }

    private static String parseJump(String input) {
        //System.out.println("jump = " + input);
        String jBits = null;
        if(input == null || input == "") {
            jBits = "000";
        }
        else {
            switch(input) {
                case "JGT":
                    jBits = "001";
                    break;
                case "JEQ":
                    jBits = "010";
                    break;
                case "JGE":
                    jBits = "011";
                    break;
                case "JLT":
                    jBits = "100";
                    break;
                case "JNE":
                    jBits = "101";
                    break;
                case "JLE":
                    jBits = "110";
                    break;
                case "JMP":
                    jBits = "111";
                    break;
            }
        }
        
        return jBits;
    }

    public static void writeOutputLine(String line, String output) {
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(output, true));
            if(line != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}