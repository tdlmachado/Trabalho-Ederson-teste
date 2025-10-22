public class FormatadorCelular {

    public static String formatar(String numero) {
        if (numero == null) return "";

        // Remove tudo que não for número
        numero = numero.replaceAll("\\D", "");

        // Verifica se o numero tem 11 digitos e formata
        if (numero.length() == 11) {
            return String.format("(%s) %s-%s",
                    numero.substring(0, 2),
                    numero.substring(2, 7),
                    numero.substring(7));
        }
        // Aqui ele verifica se o numero possui 10 digitos, e faz a formatação correta.
        else if (numero.length() == 10) {
            return String.format("(%s) %s-%s",
                    numero.substring(0, 2),
                    numero.substring(2, 6),
                    numero.substring(6));
        } else {
            // Retorna o numero para o cadastro
            return numero;
        }
    }
}