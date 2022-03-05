package com.example.NGGG.common;

import com.example.NGGG.domain.ImgCode;
import com.example.NGGG.domain.ProductImg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImgStore {

    //파일 경로
    @Value("${file.dir}/")
    private String imgDirPath;

    //확장자 추출
    private String extractExt(String originImgName) {
        int idx = originImgName.lastIndexOf(".");
        String ext = originImgName.substring(idx);
        return ext;
    }

    //저장할 파일 이름 구성
    private String createStoreImgName(String originImgName) {
        //파일이름 겹치지 않게 UUID를 통해 설정
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originImgName);
        String storeImgName = uuid + ext;

        return storeImgName;
    }

    //파일 경로 구성
    public String createPath(String storeImgName, ImgCode imgCode) {
        String viaPath = (imgCode == ImgCode.MAIN) ? "main/" : "detail/";
        return imgDirPath+viaPath+storeImgName;
    }

    //파일 저장 로직
    public ProductImg storeImg(MultipartFile multipartFile, ImgCode imgCode) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originImgName = multipartFile.getOriginalFilename();
        String storeImgName = createStoreImgName(originImgName);
        //createPath 반환 경로에 파일 저장
        multipartFile.transferTo(new File(createPath(storeImgName, imgCode)));

        return ProductImg.createProductImg(originImgName, storeImgName, imgCode);
    }

    //전체 파일 저장(상세 이미지)
    public List<ProductImg> storeDetailImgs(List<MultipartFile> multipartFiles) throws IOException {
        List<ProductImg> detailImgs = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                detailImgs.add(storeImg(multipartFile, ImgCode.DETAIL));
            }
        }
        return detailImgs;
    }
}
