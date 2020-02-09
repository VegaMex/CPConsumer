package com.vegamex.cpconsumer;

import android.net.Uri;

public class ContactCP {
    public static final Uri CONTENT_URI_CONTACTOS = Uri.parse( "content://com.vegamex.crudbasico.provider/contactos");

    public static final String[] PROJECTION_CONTACTOS =
            {"_id", "_usuario", "_email", "_telefono", "_fechaNacimiento"};

    public static final  String FIELD_ID = "_id";
    public static final  String FIELD_USUARIO = "_usuario";
    public static final  String FIELD_EMAIL = "_email";
    public static final  String FIELD_TELEFONO = "_telefono";
    public static final  String FIELD_FECHA_NACIMIENTO = "_fechaNacimiento";
}
