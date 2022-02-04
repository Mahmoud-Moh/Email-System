package com.WebApp.EmailServerClient.Folders;

public class FolderFactory {

    public static IFolder GetFolder(String Type) {
        if (Type == null) return null;

        if (Type.equalsIgnoreCase("inbox")) return new Inbox();
        else if (Type.equalsIgnoreCase("draft")) return new Draft();
        else if (Type.equalsIgnoreCase("trash")) return new Trash();
        else if (Type.equalsIgnoreCase("sent")) return new Sent();

        return null;
    }
}
