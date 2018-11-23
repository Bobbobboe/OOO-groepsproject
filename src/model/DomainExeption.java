package model;

public class DomainExeption extends RuntimeException {

    public DomainExeption(){
        super();
    }

    public DomainExeption(String error){
        super(error);
    }
}
