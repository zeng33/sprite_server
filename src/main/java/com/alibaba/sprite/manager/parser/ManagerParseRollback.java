/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.sprite.manager.parser;

import com.alibaba.sprite.core.util.ParseUtil;

/**
 * @author xianmao.hexm
 */
public final class ManagerParseRollback {

    public static final int OTHER = -1;
    public static final int CONFIG = 1;
    public static final int ROUTE = 2;
    public static final int USER = 3;

    public static int parse(String stmt, int offset) {
        int i = offset;
        for (; i < stmt.length(); i++) {
            switch (stmt.charAt(i)) {
            case ' ':
                continue;
            case '/':
            case '#':
                i = ParseUtil.comment(stmt, i);
                continue;
            case '@':
                return rollback2Check(stmt, i);
            default:
                return OTHER;
            }
        }
        return OTHER;
    }

    static int rollback2Check(String stmt, int offset) {
        if (stmt.length() > ++offset && stmt.charAt(offset) == '@') {
            if (stmt.length() > ++offset) {
                switch (stmt.charAt(offset)) {
                case 'C':
                case 'c':
                    return rollback2CCheck(stmt, offset);
                case 'R':
                case 'r':
                    return rollback2RCheck(stmt, offset);
                case 'U':
                case 'u':
                    return rollback2UCheck(stmt, offset);
                default:
                    return OTHER;
                }
            }
        }
        return OTHER;
    }

    // ROLLBACK @@CONFIG
    static int rollback2CCheck(String stmt, int offset) {
        if (stmt.length() > offset + 5) {
            char c1 = stmt.charAt(++offset);
            char c2 = stmt.charAt(++offset);
            char c3 = stmt.charAt(++offset);
            char c4 = stmt.charAt(++offset);
            char c5 = stmt.charAt(++offset);
            if ((c1 == 'O' || c1 == 'o') && (c2 == 'N' || c2 == 'n') && (c3 == 'F' || c3 == 'f')
                    && (c4 == 'I' || c4 == 'i') && (c5 == 'G' || c5 == 'g')) {
                if (stmt.length() > ++offset && stmt.charAt(offset) != ' ') {
                    return OTHER;
                }
                return CONFIG;
            }
        }
        return OTHER;
    }

    // ROLLBACK @@ROUTE
    static int rollback2RCheck(String stmt, int offset) {
        if (stmt.length() > offset + 4) {
            char c1 = stmt.charAt(++offset);
            char c2 = stmt.charAt(++offset);
            char c3 = stmt.charAt(++offset);
            char c4 = stmt.charAt(++offset);
            if ((c1 == 'O' || c1 == 'o') && (c2 == 'U' || c2 == 'u') && (c3 == 'T' || c3 == 't')
                    && (c4 == 'E' || c4 == 'e')) {
                if (stmt.length() > ++offset && stmt.charAt(offset) != ' ') {
                    return OTHER;
                }
                return ROUTE;
            }
        }
        return OTHER;
    }

    // ROLLBACK @@USER
    static int rollback2UCheck(String stmt, int offset) {
        if (stmt.length() > offset + 3) {
            char c1 = stmt.charAt(++offset);
            char c2 = stmt.charAt(++offset);
            char c3 = stmt.charAt(++offset);
            if ((c1 == 'S' || c1 == 's') && (c2 == 'E' || c2 == 'e') && (c3 == 'R' || c3 == 'r')) {
                if (stmt.length() > ++offset && stmt.charAt(offset) != ' ') {
                    return OTHER;
                }
                return USER;
            }
        }
        return OTHER;
    }

}