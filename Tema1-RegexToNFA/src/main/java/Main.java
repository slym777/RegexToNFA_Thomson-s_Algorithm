import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import exceptions.InvalidFormException;
import graph.MyJGraphXAdapter;
import graph.SymbolEdge;
import model.NFA;
import org.jgrapht.ext.JGraphXAdapter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Character> infixOperands = Arrays.asList('|', '*', '^', '(', ')');

    private static List<Character> postfixOperands = Arrays.asList('|', '*', '^', '.');

    private static final String MENU = "\nWelcome. Please choose your option: \n" +
            "1.Regex in infix form \n" +
            "2.Regex in prefix form \n" +
            "3.Regex in postfix form \n" +
            "Type quit to exit\n";

    private static final String INFO_INFIX = "\nAvailable alphabet : [a-z], [A-Z], [0-9]\n" +
            "Available operators : '|', '*', '^'\n" +
            "Enter your regex in infix form:\n";

    private static final String INFO_PREFIX = "\nAvailable alphabet : [a-z], [A-Z], [0-9]\n" +
            "Available operators : '|', '*', '^', '.'\n" +
            "Enter your regex in prefix form:\n";

    private static final String INFO_POSTFIX = "\nAvailable alphabet : [a-z], [A-Z], [0-9]\n" +
            "Available operators : '|', '*', '^', '.'\n" +
            "Enter your regex in postfix form:\n";
    private static final String INVALID_OPTION = "\nInvalid option. Try again\n";

    private static final String BYE_MESSAGE = "\nHave a nice day!";

    private static final String INVALID_REGEX_PREFIX = "Invalid regex in prefix form. Enter again:";

    private static final String INVALID_REGEX_INFIX = "Invalid regex in infix form. Enter again:";

    private static final String INVALID_REGEX_POSTFIX = "Invalid regex in postfix form. Enter again:";

    public static void main(String[] args){
        String option;
        boolean exit = false, isInfix = false, isValidated = false, isPrefix = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit){
            System.out.println(MENU);
            option = scanner.nextLine();
            switch (option.toLowerCase()) {
                case "1": {
                    System.out.println(INFO_INFIX);
                    String regex;
                    isInfix = true;
                    NFA resultedNFA = null;
                    while(!isValidated){
                        regex  = scanner.nextLine();
                        try {
                            if (!validateRegex(regex, isInfix))
                                throw new InvalidFormException(INVALID_REGEX_INFIX);
                            resultedNFA = new NFA(regex, isInfix, isPrefix);
                            isValidated = true;
                        } catch (InvalidFormException exception){
                            System.out.println(exception.getMessage());
                        } catch (Exception exception){
                            System.out.println(INVALID_REGEX_INFIX);
                        }
                    }
                    generateNFAGraph(resultedNFA);
                    exit = true;
                    break;
                }
                case "2": {
                    System.out.println(INFO_PREFIX);
                    isPrefix = true;
                    String regex;
                    NFA resultedNFA = null;
                    while(!isValidated){
                        regex  = scanner.nextLine();
                        try {
                            if (!validateRegex(regex, isInfix))
                                throw new InvalidFormException(INVALID_REGEX_PREFIX);
                            resultedNFA = new NFA(regex, isInfix, isPrefix);
                            isValidated = true;
                        } catch (InvalidFormException exception){
                            System.out.println(exception.getMessage());
                        } catch (Exception exception){
                            System.out.println(INVALID_REGEX_PREFIX);
                        }
                    }
                    generateNFAGraph(resultedNFA);
                    exit = true;
                    break;
                }
                case "3": {
                    System.out.println(INFO_POSTFIX);
                    String regex;
                    NFA resultedNFA = null;
                    while(!isValidated){
                        regex  = scanner.nextLine();
                        try {
                            if (!validateRegex(regex, isInfix))
                                throw new InvalidFormException(INVALID_REGEX_POSTFIX);
                            resultedNFA = new NFA(regex, isInfix, isPrefix);
                            isValidated = true;
                        } catch (InvalidFormException exception){
                            System.out.println(exception.getMessage());
                        } catch (Exception exception){
                            System.out.println(INVALID_REGEX_POSTFIX);
                        }
                    }
                    generateNFAGraph(resultedNFA);
                    exit = true;
                    break;
                }
                case "quit": {
                    exit = true;
                    break;
                }
                default: {
                    System.out.println(INVALID_OPTION);
                }
            }
        }
        System.out.println(BYE_MESSAGE);
    }

    private static void generateNFAGraph(NFA nfa){
        MyJGraphXAdapter applet = new MyJGraphXAdapter(nfa);
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("Resulted NFA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        saveGraph(applet);
    }

    private static void saveGraph( MyJGraphXAdapter applet){
        try {
            File imgFile = new File("src\\main\\resources\\NFA-Visualization.png");
            imgFile.createNewFile();
            JGraphXAdapter<String, SymbolEdge> jgxAdapter = applet.getJgxAdapter();
            mxIGraphLayout layout = new mxCompactTreeLayout(jgxAdapter);
            layout.execute(jgxAdapter.getDefaultParent());

            BufferedImage image = mxCellRenderer.createBufferedImage(jgxAdapter, null, 1,
                    Color.WHITE, true, null);
            ImageIO.write(image, "PNG", imgFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean validateRegex(String regex, boolean infix){
        if (regex.isEmpty())
            return false;
        for (char c: regex.toCharArray())
            if (!checkCharacter(c, infix))
                return false;
        return true;
    }

    public static boolean checkCharacter(char c, boolean infix){
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= 0 && c <= 9){
            return true;
        }
        if (infix) {
            return infixOperands.contains(c);
        } else {
            return postfixOperands.contains(c);
        }
    }

}
