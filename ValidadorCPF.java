public class ValidadorCPF {

    /**
     * Valida um CPF (apenas números).
     * Aceita CPF com ou sem pontuação (pontos e traço) — os remove antes de validar.
     */
    public static boolean isValidCPF(String cpf) {
        if (cpf == null) return false;

        // Remove tudo que não for dígito
        cpf = cpf.replaceAll("\\D", "");

        // Deve ter 11 dígitos
        if (cpf.length() != 11) return false;

        // Verifica sequências repetidas (ex: 00000000000, 11111111111, ...)
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(String.valueOf(cpf.charAt(i)));
            }

            // Calcula primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += digits[i] * (10 - i);
            }
            int remainder = (sum * 10) % 11;
            int firstCheck = (remainder == 10) ? 0 : remainder;
            if (firstCheck != digits[9]) return false;

            // Calcula segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += digits[i] * (11 - i);
            }
            remainder = (sum * 10) % 11;
            int secondCheck = (remainder == 10) ? 0 : remainder;
            return secondCheck == digits[10];

        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Formata CPF adicionando pontos e traço (ex: 12345678909 -> 123.456.789-09).
     * Se o CPF não tiver 11 dígitos, retorna o input original.
     */
    public static String formatCPF(String cpf) {
        if (cpf == null) return null;
        String onlyDigits = cpf.replaceAll("\\D", "");
        if (onlyDigits.length() != 11) return cpf;
        return onlyDigits.substring(0,3) + "." +
                onlyDigits.substring(3,6) + "." +
                onlyDigits.substring(6,9) + "-" +
                onlyDigits.substring(9);
    }
}
