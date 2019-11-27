package sangmok.util;

/**
 * This class is a Java implementation of the famous japanese JavaScript
 * obfuscator.
 *
 * @since 1.8
 * @author Gennadiy Golovin
 */
public final class aaEncode {
    /** Original JavaScript text */
    private String source;

    /** Encoded JavaScript text */
    private String encoded;

    /**
     * Creates new object and obfuscates source text given in argument.
     *
     * @param text original JavaScript source.
     */
    public aaEncode(String text) {
        if (text == null)
            return;

        String[] b = new String[] { "(c^_^o)", "(ﾟΘﾟ)", "((o^_^o) - (ﾟΘﾟ))", "(o^_^o)", "(ﾟｰﾟ)", "((ﾟｰﾟ) + (ﾟΘﾟ))",
                "((o^_^o) +(o^_^o))", "((ﾟｰﾟ) + (o^_^o))", "((ﾟｰﾟ) + (ﾟｰﾟ))", "((ﾟｰﾟ) + (ﾟｰﾟ) + (ﾟΘﾟ))", "(ﾟДﾟ) .ﾟωﾟﾉ",
                "(ﾟДﾟ) .ﾟΘﾟﾉ", "(ﾟДﾟ) ['c']", "(ﾟДﾟ) .ﾟｰﾟﾉ", "(ﾟДﾟ) .ﾟДﾟﾉ", "(ﾟДﾟ) [ﾟΘﾟ]" };
        StringBuilder r = new StringBuilder(
                "ﾟωﾟﾉ= /｀ｍ´）ﾉ ~┻━┻   //*´∇｀*/ ['_']; o=(ﾟｰﾟ)  =_=3; c=(ﾟΘﾟ) =(ﾟｰﾟ)-(ﾟｰﾟ); ");

        if (text.matches("/ひだまりスケッチ×(365|３５６)\\s*来週も見てくださいね[!！]/")) {
            r.append("X=_=3; ");
            r.append("\r\n\r\n    X / _ / X < \"来週も見てくださいね!\";\r\n\r\n");
        }

        r.append("(ﾟДﾟ) =(ﾟΘﾟ)= (o^_^o)/ (o^_^o);" + "(ﾟДﾟ)={ﾟΘﾟ: '_' ,ﾟωﾟﾉ : ((ﾟωﾟﾉ==3) +'_') [ﾟΘﾟ] "
                + ",ﾟｰﾟﾉ :(ﾟωﾟﾉ+ '_')[o^_^o -(ﾟΘﾟ)] "
                + ",ﾟДﾟﾉ:((ﾟｰﾟ==3) +'_')[ﾟｰﾟ] }; (ﾟДﾟ) [ﾟΘﾟ] =((ﾟωﾟﾉ==3) +'_') [c^_^o];"
                + "(ﾟДﾟ) ['c'] = ((ﾟДﾟ)+'_') [ (ﾟｰﾟ)+(ﾟｰﾟ)-(ﾟΘﾟ) ];" + "(ﾟДﾟ) ['o'] = ((ﾟДﾟ)+'_') [ﾟΘﾟ];"
                + "(ﾟoﾟ)=(ﾟДﾟ) ['c']+(ﾟДﾟ) ['o']+(ﾟωﾟﾉ +'_')[ﾟΘﾟ]+ ((ﾟωﾟﾉ==3) +'_') [ﾟｰﾟ] + "
                + "((ﾟДﾟ) +'_') [(ﾟｰﾟ)+(ﾟｰﾟ)]+ ((ﾟｰﾟ==3) +'_') [ﾟΘﾟ]+" + "((ﾟｰﾟ==3) +'_') [(ﾟｰﾟ) - (ﾟΘﾟ)]+(ﾟДﾟ) ['c']+"
                + "((ﾟДﾟ)+'_') [(ﾟｰﾟ)+(ﾟｰﾟ)]+ (ﾟДﾟ) ['o']+" + "((ﾟｰﾟ==3) +'_') [ﾟΘﾟ];(ﾟДﾟ) ['_'] =(o^_^o) [ﾟoﾟ] [ﾟoﾟ];"
                + "(ﾟεﾟ)=((ﾟｰﾟ==3) +'_') [ﾟΘﾟ]+ (ﾟДﾟ) .ﾟДﾟﾉ+"
                + "((ﾟДﾟ)+'_') [(ﾟｰﾟ) + (ﾟｰﾟ)]+((ﾟｰﾟ==3) +'_') [o^_^o -ﾟΘﾟ]+"
                + "((ﾟｰﾟ==3) +'_') [ﾟΘﾟ]+ (ﾟωﾟﾉ +'_') [ﾟΘﾟ]; " + "(ﾟｰﾟ)+=(ﾟΘﾟ); (ﾟДﾟ)[ﾟεﾟ]='\\\\'; "
                + "(ﾟДﾟ).ﾟΘﾟﾉ=(ﾟДﾟ+ ﾟｰﾟ)[o^_^o -(ﾟΘﾟ)];" + "(oﾟｰﾟo)=(ﾟωﾟﾉ +'_')[c^_^o];" + // TODO
                "(ﾟДﾟ) [ﾟoﾟ]='\\\"';" + "(ﾟДﾟ) ['_'] ( (ﾟДﾟ) ['_'] (ﾟεﾟ+");

        r.append("(ﾟДﾟ)[ﾟoﾟ]+ ");

        for (int i = 0; i < text.length(); i++) {
            int n = text.charAt(i);
            StringBuilder t = new StringBuilder("(ﾟДﾟ)[ﾟεﾟ]+");
            if (n <= 127) {
                char[] chars = Integer.toOctalString(n).toCharArray();
                for (char c : chars)
                    t.append(b[Integer.parseInt(String.valueOf(c))]).append("+ ");
            } else {
                String hexString = "000" + Integer.toHexString(n);
                char[] chars = hexString.substring(hexString.length() - 4).toCharArray();
                t.append("(oﾟｰﾟo)+ ");
                for (char c : chars)
                    t.append(b[Integer.parseUnsignedInt(String.valueOf(c), 16)]).append("+ ");
            }
            r.append(t);
        }
        r.append("(ﾟДﾟ)[ﾟoﾟ]) (ﾟΘﾟ)) ('_');");

        this.source = text;
        this.encoded = r.toString();
    }

    //////// //////// //////// //////// //////// //////// //////// ////////

    /**
     * Returns source JavaScript text, or {@code null} if there is no such text.
     *
     * @return {@link #source} JavaScript text, or {@code null} if there is no such
     *         text
     */
    public String getSource() {
        return this.source;
    }

    /**
     * Returns encoded JavaScript text, or {@code null} if there is no such text.
     *
     * @return {@link #encoded} JavaScript text, or {@code null} if there is no such
     *         text
     */
    public String get() {
        return this.encoded;
    }
}
