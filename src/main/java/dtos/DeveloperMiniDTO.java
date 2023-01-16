package dtos;

import entities.Account;
import entities.Developer;
import entities.ProjectHour;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

public class DeveloperMiniDTO {


    private Integer developerId;

    private Integer accountId;
    private String accountName;


    public DeveloperMiniDTO(Developer developer) {
        this.developerId = developer.getDeveloperId();
        this.accountId = developer.getAccount().getAccountId();
        this.accountName = developer.getAccount().getAccountName();
    }


    public static List<DeveloperMiniDTO> listToDto(List<Developer> entities) {
        return entities.stream().map(DeveloperMiniDTO::new).collect(Collectors.toList());
    }
}
