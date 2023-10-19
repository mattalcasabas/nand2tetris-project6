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
        char instType = null;
        String opcode = null;

        // if the line is a comment
        if (input.startsWith("//")) {
            return null;
        }
        // if the line is an A-instruction
        else if (input.startsWith("@")) {
            instType = 'A';
            opcode = "000";
        }
        else if (input.startWith("")) {

        }

        switch(instType) {
            case 'A':
                // code for converting A-type instructions
                break;
            case 'C':
                // code for converting C-type instructions
                break;
            default:
                System.out.println("Unknown instruction type");
                System.exit(1);
        }

        return null;
    }

    public static void writeOutputLine(String line, String output) {
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(output, true));
            writer.write(line);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}