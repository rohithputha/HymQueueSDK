package org.hitro.config;

import lombok.Getter;


public class Constants {

    @Getter private final static byte backslash = 92;

    @Getter private final static byte endByte = 113;

    @Getter private final static byte[] commandSeperator = {backslash,endByte};
}
