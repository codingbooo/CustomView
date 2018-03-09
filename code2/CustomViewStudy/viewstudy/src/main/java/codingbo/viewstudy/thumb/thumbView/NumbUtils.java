package codingbo.viewstudy.thumb.thumbView;

/**
 * Created by bob
 * on 18.3.7.
 */

public class NumbUtils {

    public static String[] calculator(int count, int gap) {
        StringBuilder num0 = new StringBuilder();
        String num1 = String.valueOf(count);
        String num2 = String.valueOf(count + gap);

        if (num2.length() > num1.length()) {
            return getNumArray(num0.toString(), num1, num2);
        }
        int length = num1.length();

        for (int i = 0; i < length; i++) {
            if (num1.charAt(i) == num2.charAt(i)) {
                num0.append(num1.charAt(i));
            } else {
                num1 = num1.substring(i, num1.length());
                num2 = num2.substring(i, num2.length());
                break;
            }
        }
        return getNumArray(num0.toString(), num1, num2);
    }

    public static String[] getNumArray(String num0, String num1, String num2) {
        return new String[]{num0, num1, num2};
    }
}
