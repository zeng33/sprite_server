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
package com.alibaba.sprite.manager.handler;

import static com.alibaba.sprite.manager.parser.ManagerParseSelect.SESSION_AUTO_INCREMENT;
import static com.alibaba.sprite.manager.parser.ManagerParseSelect.VERSION_COMMENT;

import com.alibaba.sprite.core.ErrorCode;
import com.alibaba.sprite.manager.ManagerConnection;
import com.alibaba.sprite.manager.parser.ManagerParseSelect;
import com.alibaba.sprite.manager.response.SelectSessionAutoIncrement;
import com.alibaba.sprite.manager.response.SelectVersionComment;

/**
 * @author xianmao.hexm
 */
public final class SelectHandler {

    public static void handle(String query, ManagerConnection c, int offset) {
        switch (ManagerParseSelect.parse(query, offset)) {
        case VERSION_COMMENT:
            SelectVersionComment.execute(c);
            break;
        case SESSION_AUTO_INCREMENT:
            SelectSessionAutoIncrement.execute(c);
            break;
        default:
            c.writeErrMessage((byte) 1, ErrorCode.ER_YES, "Unsupported statement");
        }
    }

}