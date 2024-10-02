package com.aplikasi.binarfudv2.util.sp;

import org.springframework.stereotype.Component;

@Component
public class MerchantQuerySP {

    public String getData ="CREATE OR REPLACE FUNCTION public.getMerchant()\n" +
            " RETURNS TABLE(resid bigint, resname varchar, reslocation varchar)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $function$\n" +
            "\tbegin\n" +
            "\tRETURN QUERY\n" +
            "      select id bigint, merchant_name varchar, merchant_location varchar from merchant m;\n" +
            "\tEND;\n" +
            "$function$\n" +
            ";\n";

    public String getDataMerchantLikeName ="CREATE OR REPLACE FUNCTION public.listmerchant(rqnama varchar)\n" +
            " RETURNS TABLE(resid bigint, resname varchar, reslocation varchar)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $function$\n" +
            "           DECLARE \n" +
            "               var_r record;\n" +
            "           BEGIN\n" +
            "               FOR var_r IN(SELECT \n" +
            "                           id,\n" +
            "                           merchant_name,\n" +
            "                           merchant_location \n" +
            "                           FROM merchant\n" +
            "                           WHERE merchant_name ILIKE  rqnama)  \n" +
            "               LOOP\n" +
            "                   resid :=var_r.id  ;\n" +
            "                   resname :=var_r.merchant_name;\n" +
            "                   reslocation :=var_r.merchant_location;\n" +
            "                   RETURN NEXT;\n" +
            "               END LOOP;\n" +
            "           end $function$\n" +
            ";\n";

    public String insertMerchant ="CREATE OR REPLACE PROCEDURE public.insert(INOUT resid bigint, INOUT rqnama varchar)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $procedure$\n" +
            "            begin\n" +
            "            if rqnama is null Then \n" +
            "              raise notice 'nama kosong';\n" +
            "             rqnama = 'nama wajib diisi';\n" +
            "             return;\n" +
            "            else \n" +
            "             raise notice 'nama  ada';\n" +
            "            end if;  \n" +
            "                 INSERT into public.merchant\n" +
            "                 (id, merchant_name, merchant_location, created_date, updated_date, open) \n" +
            "                 SELECT nextval('merchant_id_seq'),\n" +
            "                      rqnama,\n" +
            "                      'jaktim',\n" +
            "                      now(),\n" +
            "                      now(),\n" +
            "                       true\n" +
            "                RETURNING id INTO resid; \n" +
            "               rqnama=rqnama;\n" +
//            "                 commit;\n" +  -- Tidak perlu COMMIT di sini, biarkan aplikasi Anda yang mengelola transaksi.
            "            END;\n" +
            "            $procedure$\n" +
            ";";

    public String updateMerchant = "CREATE OR REPLACE PROCEDURE public.update_merchant(INOUT resid bigint, INOUT rqnama varchar)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $procedure$\n" +
            "            begin\n" +
            "            if rqnama is null Then \n" +
            "              raise notice 'nama kosong';\n" +
            "             rqnama = 'nama wajib diisi';\n" +
            "             return;\n" +
            "            else \n" +
            "             raise notice 'nama  ada';\n" +
            "            end if;  \n" +
            "                 update merchant\n" +
            "                 set merchant_name=rqnama\n" +
            "                 where id = resid;\n" +
            "               rqnama=rqnama;\n" +
//            "                 commit;\n" +  -- Tidak perlu COMMIT di sini, biarkan aplikasi Anda yang mengelola transaksi.
            "            END;\n" +
            "            $procedure$\n" +
            ";\n";

    public String deletedMerchant ="CREATE OR REPLACE PROCEDURE public.deleted_merchant(INOUT resid bigint)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $procedure$\n" +
            "            begin \n" +
            "                 update merchant\n" +
            "                 set deleted_date =now()\n" +
            "                 where id = resid;\n" +
//            "                 commit;\n" +  -- Tidak perlu COMMIT di sini, biarkan aplikasi Anda yang mengelola transaksi.
            "            END;\n" +
            "            $procedure$\n" +
            ";\n";
}