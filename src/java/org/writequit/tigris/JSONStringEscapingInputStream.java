package org.writequit.tigris;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.Override;
import java.lang.System;
import java.util.LinkedList;
import java.util.List;

/**
 * Things!
 */
public class JSONStringEscapingInputStream extends PushbackInputStream {
    final private int BS = 8;
    final private int TAB = 9;
    final private int LF = 10;
    final private int FF = 12;
    final private int CR = 13;

    public JSONStringEscapingInputStream(InputStream source) {
        super(source, 5);
    }

    @Override
    public int read() throws java.io.IOException {
        if (this.pos != this.buf.length) {
            // we have some lookahead, so return that
            return super.read();
        }

        int nextByte = super.read();
        if (nextByte == -1) {
            return -1;
        }

        switch (nextByte) {
            case BS:
                unread('b');
                return '\\';
            case TAB:
                unread('t');
                return '\\';
            case LF:
                unread('n');
                return '\\';
            case FF:
                unread('f');
                return '\\';
            case CR:
                unread('r');
                return '\\';
            case '"':
                unread('"');
                return '\\';
            case '\\':
                unread('\\');
                return '\\';
            default:
                if (nextByte < 32) {
                    // push the hex representation in reverse order followed by 'u'
                    String num = Integer.toHexString(nextByte);
                    for (int i = 0; i < 4; i++) {
                        if (i < num.length()) {
                            unread(num.codePointAt(num.length() - 1 - i));
                        } else {
                            unread('0');
                        }
                    }
                    unread('u');
                    return '\\';
                } else {
                    return nextByte;
                }
        }
    }

    @Override
    public int read(byte[] b) throws java.io.IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws java.io.IOException {
        if (len == 0) {
            return 0;
        }

        int nextByte = this.read();
        if (nextByte == -1) {
            return -1;
        }

        int i;
        for (i = off; i < len && nextByte != -1; i++) {
            // update the byte in the buffer
            b[i] = (byte) nextByte;

            // increment the number of bytes read
            nextByte = this.read();
        }
        return i - off;
    }

    @Override
    public long skip(long n) throws java.io.IOException {
        return super.skip(n);
    }

    @Override
    public int available() throws java.io.IOException {
        return super.available();
    }
}
