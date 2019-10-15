package com.stylefeng.guns.rest.modular.service.impl;

import com.alibaba.dubbo.common.bytecode.Wrapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.cinema.bean.*;
import com.stylefeng.guns.rest.cinema.service.CinemaService;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    MtimeCinemaTMapper cinemaTMapper;

    @Override
    public String selectNameById(Integer id) {
        return null;
    }

    @Override
    public CinemaResponseVo getCinemas(CinemaBeanVo cinemaBeanVo) {
        Page page = new Page<>();
        EntityWrapper<MtimeCinemaT> EntityWrapper = new EntityWrapper<>();
        List<MtimeCinemaT> mtimeCinemaTS1 = cinemaTMapper.selectPage(page, EntityWrapper);
        long total = page.getTotal();
        CinemaResponseVo cinemaResponseVo = new CinemaResponseVo();
        cinemaResponseVo.setMtimeCinemaTS(mtimeCinemaTS1);
        cinemaResponseVo.setNowPage(cinemaBeanVo.getNowPage());
        cinemaResponseVo.setTotalPage((int) total);

        return cinemaResponseVo;
    }

    @Override
    public ConditionVo getCondition(ConditionBeanVo conditionBeanVo) {
        ConditionVo conditionVo = new ConditionVo();
        List<AreaVo> areaVos = cinemaTMapper.selectArea();
        AreaVo areaVo = new AreaVo();
        areaVo.setAreaId(99);
        areaVo.setAreaName("全部");
        areaVo.setActive(false);
        areaVos.add(areaVo);

        for (AreaVo areaVo1 : areaVos) {
            areaVo.setActive(false);
            if(conditionBeanVo.getAreaId()==areaVo1.getAreaId()){
                areaVo1.setActive(true);
            }
        }

        List<BrandVo> brandVos = cinemaTMapper.selectBrand();
        BrandVo brandVo1 = new BrandVo();
        brandVo1.setActive(false);
        brandVo1.setBrandId(99);
        brandVo1.setBrandName("全部");
        brandVos.add(brandVo1);

        for (BrandVo brandVo : brandVos) {
            brandVo.setActive(false);
            if(conditionBeanVo.getBrandId()==brandVo.getBrandId()){
                brandVo.setActive(true);
            }
        }

        List<HalltypeVo> halltypeVos = cinemaTMapper.selectHallype();
        HalltypeVo halltypeVo1 = new HalltypeVo();
        halltypeVo1.setActive(false);
        halltypeVo1.setHalltypeId(99);
        halltypeVo1.setHalltypeName("全部");
        halltypeVos.add(halltypeVo1);

        for (HalltypeVo halltypeVo : halltypeVos) {
            halltypeVo.setActive(false);
            if(conditionBeanVo.getHallType()==conditionBeanVo.getHallType()){
                halltypeVo.setActive(true);
            }
        }

        conditionVo.setAreaList(areaVos);
        conditionVo.setBrandList(brandVos);
        conditionVo.setHalltypeList(halltypeVos);

        return conditionVo;
    }
    @Override
        public FieldInfoVO getFields(Integer cinemaId){
            MtimeCinemaT mtimeCinemaT = cinemaTMapper.selectById(cinemaId);
            FieldCinemaVO cinemaVO = converter2FieldCinemaVO(mtimeCinemaT);
            List<FieldFilmInfoVO> filmList = cinemaTMapper.selectFieldFilmInfoVOs(cinemaId);
            for (FieldFilmInfoVO fieldFilmInfoVO : filmList) {
                List<FieldVO> fields = getFilmFields(cinemaId, fieldFilmInfoVO.getFilmId());
                fieldFilmInfoVO.setFilmFields(fields);
            }

            FieldInfoVO fieldInfoVO = new FieldInfoVO();
            fieldInfoVO.setCinemaInfo(cinemaVO);
            fieldInfoVO.setFilmList(filmList);
            return fieldInfoVO;

        }

        @Override
        public FieldDetailInfoVO getFieldInfo (Integer cinemaId, Integer fieldId){
            FieldCinemaVO cinemaInfo = cinemaTMapper.selectCinemaInfoById(cinemaId);
            FieldFilmInfoVO filmInfo = cinemaTMapper.selectFieldFilmInfoVO(cinemaId, fieldId);
            HallInfoVO hallInfo = cinemaTMapper.selectHallInfo(fieldId);
            FieldDetailInfoVO fieldDetailInfoVO = new FieldDetailInfoVO();
            fieldDetailInfoVO.setCinemaInfo(cinemaInfo);
            fieldDetailInfoVO.setFilmInfo(filmInfo);
            fieldDetailInfoVO.setHallInfo(hallInfo);
            return fieldDetailInfoVO;
        }

        private List<FieldVO> getFilmFields (Integer cinemaId, Integer filmId){
            return cinemaTMapper.selectFilmFields(cinemaId, filmId);
        }

        private FieldCinemaVO converter2FieldCinemaVO (MtimeCinemaT mtimeCinemaT){
            FieldCinemaVO cinemaVO = new FieldCinemaVO();
            cinemaVO.setCinemaAdress(mtimeCinemaT.getCinemaAddress());
            cinemaVO.setCinemaId(mtimeCinemaT.getUuid());
            cinemaVO.setCinemaName(mtimeCinemaT.getCinemaName());
            cinemaVO.setCinemaPhone(mtimeCinemaT.getCinemaPhone());
            cinemaVO.setImgUrl(mtimeCinemaT.getImgAddress());
            return cinemaVO;
        }
    }

