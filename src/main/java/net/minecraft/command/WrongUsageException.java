package net.minecraft.command;

public class WrongUsageException extends SyntaxErrorException {
    public WrongUsageException(String text, Object... aobject) {
        super(text, aobject);
    }
}
