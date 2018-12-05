/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha.pkg1;

/**
 *
 * @author ziad
 */
public class SHA {

    /*
     * Bitwise rotate a 32-bit number to the left
     */
    private static int rol(int num, int cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    }

    /*
     * Take a string and return the base64 representation of its SHA-1.
     */
    public String encodeBase64(String str) {

        // Convert a string to a sequence of 16-word blocks, stored as an array.
        // Append padding bits and the length, as described in the SHA standard

        byte[] x = str.getBytes();
        int[] blks = new int[(((x.length + 8) >> 6) + 1) * 16];
        int i;

        for(i = 0; i < x.length; i++) {
            blks[i >> 2] |= x[i] << (24 - (i % 4) * 8);
        }

        blks[i >> 2] |= 0x80 << (24 - (i % 4) * 8);
        blks[blks.length - 1] = x.length * 8;

        // calculate 160 bit SHA hash of the sequence of blocks

        int[] w = new int[80];

        int a =  1732584193;
        int b = -271733879;
        int c = -1732584194;
        int d =  271733878;
        int e = -1009589776;

        for(i = 0; i < blks.length; i += 16) {
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            int olde = e;

            for(int j = 0; j < 80; j++) {
                w[j] = (j < 16) ? blks[i + j] :
                       ( rol(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1) );

                int t = rol(a, 5) + e + w[j] +
                   ( (j < 20) ?  1518500249 + ((b & c) | ((~b) & d))
                   : (j < 40) ?  1859775393 + (b ^ c ^ d)
                   : (j < 60) ? -1894007588 + ((b & c) | (b & d) | (c & d))
                   : -899497514 + (b ^ c ^ d) );
                e = d;
                d = c;
                c = rol(b, 30);
                b = a;
                a = t;
              }

              a = a + olda;
              b = b + oldb;
              c = c + oldc;
              d = d + oldd;
              e = e + olde;
          }

          // Convert 160 bit hash to base64

          int[] words = {a,b,c,d,e,0};
          byte[] base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes();
          byte[] result = new byte[28];
          for (i=0; i<27; i++) {
             int start=i*6;
             int word=start>>5;
             int offset=start & 0x1f;

             if (offset <= 26) {
                 result[i] = base64[(words[word] >> (26 - offset)) & 0x3F];
             } else if (offset == 28) {
                 result[i] = base64[(((words[word] & 0x0F) << 2) |
                                    ((words[word+1] >> 30) & 0x03)) & 0x3F];
             } else {
                 result[i] = base64[(((words[word] & 0x03) << 4) |
                                    ((words[word+1] >> 28) & 0x0F)) & 0x3F];
             }
          }
          result[27]='=';

          return new String(result);
    }

    /*
     * Take a string and return the base64 representation of its SHA-1.
     */
    public String encodeHex(String str) {

        // Convert a string to a sequence of 16-word blocks, stored as an array.
        // Append padding bits and the length, as described in the SHA standard

        byte[] x = str.getBytes();
        int[] blks = new int[(((x.length + 8) >> 6) + 1) * 16];
        int i;

        for(i = 0; i < x.length; i++) {
            blks[i >> 2] |= x[i] << (24 - (i % 4) * 8);
        }

        blks[i >> 2] |= 0x80 << (24 - (i % 4) * 8);
        blks[blks.length - 1] = x.length * 8;

        // calculate 160 bit SHA hash of the sequence of blocks

        int[] w = new int[80];

        int a =  1732584193;
        int b = -271733879;
        int c = -1732584194;
        int d =  271733878;
        int e = -1009589776;

        for(i = 0; i < blks.length; i += 16) {
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            int olde = e;

            for(int j = 0; j < 80; j++) {
                w[j] = (j < 16) ? blks[i + j] :
                       ( rol(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1) );

                int t = rol(a, 5) + e + w[j] +
                   ( (j < 20) ?  1518500249 + ((b & c) | ((~b) & d))
                   : (j < 40) ?  1859775393 + (b ^ c ^ d)
                   : (j < 60) ? -1894007588 + ((b & c) | (b & d) | (c & d))
                   : -899497514 + (b ^ c ^ d) );
                e = d;
                d = c;
                c = rol(b, 30);
                b = a;
                a = t;
              }

              a = a + olda;
              b = b + oldb;
              c = c + oldc;
              d = d + oldd;
              e = e + olde;
          }

          // Convert 160 bit hash to base64


          int[] words = {a,b,c,d,e};
          StringBuilder sb = new StringBuilder();

          for(int word : words) {
              String hexWord = Integer.toHexString(word);
              //Because to hexstring apparently doesn't pad?
              while(hexWord.length() < 8) {
                  hexWord = "0" + hexWord;
              }
              sb.append(hexWord);
          }

          return sb.toString();
    }
}