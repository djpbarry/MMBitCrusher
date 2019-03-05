/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmbitcrusher;

/**
 *
 * @author David Barry <david.barry at crick dot ac dot uk>
 */
public class VariableMap {

    public static final String[] BIT_DEPTH_OPTIONS = new String[]{"8-bit", "16-bit"};
    public static final String[] BINNING_OPTIONS = new String[]{"1x1", "2x2", "4x4", "8x8"};
    public static final String BIT_DEPTH_STRING = "bit depth";
    public static final String BINNING_STRING = "binning";
    public static final String SCALING_STRING = "scaling";

    public static BitDepth getBitDepth(int bitDepthValue) {
        for (BitDepth b : BitDepth.values()) {
            if (bitDepthValue == b.getBitDepthValue()) {
                return b;
            }
        }
        return null;
    }

    public static Binning getBinning(int binVal) {
        for (Binning b : Binning.values()) {
            if (binVal == b.getBinValue()) {
                return b;
            }
        }
        return null;
    }

    public static BitDepth getBitDepth(String bitDepth) {
        for (BitDepth b : BitDepth.values()) {
            if (bitDepth.contentEquals(b.getBitDepthText())) {
                return b;
            }
        }
        return null;
    }

    public static Binning getBinning(String binning) {
        for (Binning b : Binning.values()) {
            if (binning.contentEquals(b.getBinText())) {
                return b;
            }
        }
        return null;
    }

    public enum BitDepth {
        EIGHT_BIT(BIT_DEPTH_OPTIONS[0], 8),
        SIXTEEN_BIT(BIT_DEPTH_OPTIONS[1], 16);

        private BitDepth(String bitDepthText, int bitDepthValue) {
            this.bitDepthText = bitDepthText;
            this.bitDepthValue = bitDepthValue;
        }

        private final String bitDepthText;
        private final int bitDepthValue;

        public String getBitDepthText() {
            return bitDepthText;
        }

        public int getBitDepthValue() {
            return bitDepthValue;
        }

        public String toString() {
            return this.bitDepthText;
        }
    }

    public enum Binning {
        ONE(BINNING_OPTIONS[0], 1),
        TWO(BINNING_OPTIONS[1], 2),
        FOUR(BINNING_OPTIONS[2], 4),
        EIGHT(BINNING_OPTIONS[3], 8);

        private Binning(String binText, int binValue) {
            this.binText = binText;
            this.binValue = binValue;
        }

        private final String binText;
        private final int binValue;

        public String getBinText() {
            return binText;
        }

        public int getBinValue() {
            return binValue;
        }

        public String toString() {
            return this.binText;
        }
    }
}
