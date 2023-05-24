package ua.donetc.project2boot.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.donetc.project2boot.dto.CodeDTO;
import ua.donetc.project2boot.entity.Code;
import ua.donetc.project2boot.services.CodeService;
import ua.donetc.project2boot.util.CustomResponse;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
@Transactional(readOnly = true)
public class CodeController {

    private final CodeService codeService;
    private final  ModelMapper modelMapper;


    @Autowired
    public CodeController(CodeService codeService, ModelMapper modelMapper) {
        this.codeService = codeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/codes")
    public CustomResponse<Code> getAll() {
        return codeService.getAllCodes();
    }

    @GetMapping("/codes/{title}")
    public CustomResponse<Code> getCodeByTitle(@PathVariable("title") String title) {
        return codeService.getCodeByTitle(title);
    }

    @Transactional
    @PostMapping("/codes")
    public CustomResponse<Code> addCustomCode(@RequestBody CodeDTO codeDTO) {
        return codeService.addCode(convertToCode(codeDTO));
    }

    private Code convertToCode(CodeDTO codeDTO) {
        Code code = modelMapper.map(codeDTO, Code.class);
        enrichCode(code);
        return code;
    }

    private void enrichCode(Code code) {
        code.setCreatedAt(LocalDateTime.now());
    }

    @Transactional
    @DeleteMapping("/codes/{title}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("title")String name){
        codeService.deleteCode(name);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("/codes/{title}")
    public ResponseEntity<Code> updateCode(@PathVariable("title") String title, @RequestBody Code updatingCode){
      Code exCode = codeService.get(title);
      exCode.setName(updatingCode.getName());
      updatingCode = codeService.updateCode(updatingCode);
      return ResponseEntity.ok(updatingCode);

    }
    
    @GetMapping("/codes/with/{title}")
    public CustomResponse<Code> getCodeByNameStartingWith(@PathVariable("title") String title){
        return codeService.getCodeByNameStartingWith(title);
    }



}
