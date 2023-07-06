package com.openwell.flashsale.mall.untis;

public class LuaUntils {

    public static  String STOCK_LUA = null;

    public static  String STOCK_LUA_1  = null;

    public static  String STOCK_LUA_INCR  = null;



    public static String getStock() {


            /**
             *
             * @desc 扣减库存Lua脚本
             * 库存（stock）-1：表示不限库存
             * 库存（stock）0：表示没有库存
             * 库存（stock）大于0：表示剩余库存
             *
             * @params 库存key
             * @return
             * 		-3:库存未初始化
             * 		-2:库存不足
             * 		-1:不限库存
             * 		大于等于0:剩余库存（扣减之后剩余的库存）
             * 	    redis缓存的库存(value)是-1表示不限库存，直接返回1
             */
            StringBuilder sb = new StringBuilder();
            sb.append("if (redis.call('exists', KEYS[1]) == 1) then");
            sb.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
            sb.append("    local num = tonumber(ARGV[1]);");
            sb.append("    if (stock == -1) then");
            sb.append("        return -1;");
            sb.append("    end;");
            sb.append("    if (stock >= num) then");
            sb.append("        return redis.call('incrby', KEYS[1], 0 - num);");
            sb.append("    end;");
            sb.append("    return -2;");
            sb.append("end;");
            sb.append("return -3;");
            STOCK_LUA = sb.toString();

            StringBuilder sb1 = new StringBuilder();
            sb1.append("if (redis.call('exists', KEYS[1]) == 1) then");
            sb1.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
            sb1.append("    local num = tonumber(ARGV[1]);");
            sb1.append("    if (stock >= num) then");
            sb1.append("        return redis.call('incrby', KEYS[1], 0 - num);");
            sb1.append("    end;");
            sb1.append("    return -2;");
            sb1.append("end;");
            sb1.append("return -3;");
            STOCK_LUA_1 = sb1.toString();

            StringBuilder sb2 = new StringBuilder();
            sb2.append("if (redis.call('exists', KEYS[1]) == 1) then");
            sb2.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
            sb2.append("    local num = tonumber(ARGV[1]);");
            sb2.append("    if (stock >= 0) then");
            sb2.append("        return redis.call('incrby', KEYS[1], num);");
            sb2.append("    end;");
            sb2.append("    return -2;");
            sb2.append("end;");
            sb2.append("return -3;");
            STOCK_LUA_INCR = sb2.toString();

            return STOCK_LUA;




    }
}
