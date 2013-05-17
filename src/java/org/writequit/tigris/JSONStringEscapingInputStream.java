package org.writequit.tigris;

import java.io.InputStream;
import java.lang.Override;
import java.lang.System;
import java.util.LinkedList;
import java.util.List;

/**
 * Things!
 */
public class JSONStringEscapingInputStream extends InputStream {

    final private InputStream source;
    private int byteBucket = -1;
    private LinkedList<Byte> byteQueue = new LinkedList<Byte>();
    final private int ESCAPER = 92;

    public JSONStringEscapingInputStream(InputStream source) {
        this.source = source;
    }

    @Override
    public int read() throws java.io.IOException {
        // Render byte from bytebucket
        if (!this.byteQueue.isEmpty()) {
            return (int)this.byteQueue.pop();
        }

        int nextByte = this.source.read();


        switch (nextByte) {
            case 92:
                this.byteQueue.push(new Byte((byte)nextByte));
                return ESCAPER;
            case 34:
                this.byteQueue.push(new Byte((byte)nextByte));
                return ESCAPER;
            case 9:
                this.byteQueue.push(new Byte((byte)116));
                return ESCAPER;
            case 10:
                this.byteQueue.push(new Byte((byte)110));
                return ESCAPER;
            case 8:
                this.byteQueue.push(new Byte((byte)98));
                return ESCAPER;
            case 13:
                this.byteQueue.push(new Byte((byte)114));
                return ESCAPER;
            default:
                return nextByte;
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

        int count = 0;
        for (int i = off; i < len; i++) {
            int nextByte = this.read();

            if (nextByte == -1) {
                // if we haven't gotten any bytes and immediately received a -1
                if (count == 0) {
                    // then return that we don't have anything
                    return -1;
                } else {
                    // otherwise, return the number of bytes we've read
                    return count;
                }
            }

            // update the byte in the buffer
            b[i] = (byte)nextByte;

            // increment the number of bytes read
            count++;
        }
        return count;
    }

    @Override
    public long skip(long n) throws java.io.IOException {
        return this.source.skip(n);
    }

    @Override
    public int available() throws java.io.IOException {
        return this.source.available();
    }

    @Override
    public void close() throws java.io.IOException {
        this.source.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        // no-op
    }

    @Override
    public synchronized void reset() throws java.io.IOException {
        // no-op
    }

    @Override
    public boolean markSupported() {
        return false;
    }
}