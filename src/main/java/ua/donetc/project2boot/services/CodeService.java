package ua.donetc.project2boot.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ua.donetc.project2boot.entity.Code;
import ua.donetc.project2boot.repositories.CodeRepo;
import ua.donetc.project2boot.util.CustomResponse;
import ua.donetc.project2boot.util.CustomStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
@Slf4j
@Component
public class CodeService {

    private final CodeRepo codeRepo;


    public Code save(String codeName) {
        Code code = new Code();
        code.setName(codeName);
        code.setCreatedAt(LocalDateTime.now());
        return codeRepo.save(code);
    }


    public Code get(String codeName) {
        return codeRepo.getByName(codeName).orElseGet(() -> save(codeName));
    }

    public List<Code> getAll() {
        return codeRepo.findAll();
    }

    public CustomResponse<Code> getAllCodes() {
        List<Code> codes = codeRepo.findAll();
        return new CustomResponse<>(codes, CustomStatus.SUCCESS);
    }

    public CustomResponse<Code> getCodeByTitle(String name) {
        Code code = codeRepo.getByName(name).orElseThrow();
        return new CustomResponse<>(Stream.of(code).collect(Collectors.toList()), CustomStatus.SUCCESS);
    }

    public CustomResponse<Code> addCode(Code code) {
        Code newCode = codeRepo.save(code);
        return new CustomResponse<>(Stream.of(newCode).collect(Collectors.toList()), CustomStatus.SUCCESS);

    }

    public void deleteCode(String name) {
        codeRepo.deleteByName(name);
    }

    public Code updateCode(Code updatingCode) {
        return codeRepo.save(updatingCode);
    }

    public CustomResponse<Code> getCodeByNameStartingWith(String name) {
        List<Code> list = codeRepo.getCodeByNameStartingWith(name);
        if (list.isEmpty()) {
            return new CustomResponse<>(list, CustomStatus.NOT_FOUND);
        } else
            return new CustomResponse<>(list, CustomStatus.SUCCESS);
    }
    public List<Code> getCodeByNameStartingWithEE(String name){
        return codeRepo.getCodeByNameStartingWith(name);
    }

    public Page<Code> findAllCodeWithPagination(Integer page, Integer codePerPage) {
        PageRequest pageRequest = PageRequest.of(page, codePerPage);
        return codeRepo.findAll(pageRequest);

        //TODO Parameters  Page
    }
}



