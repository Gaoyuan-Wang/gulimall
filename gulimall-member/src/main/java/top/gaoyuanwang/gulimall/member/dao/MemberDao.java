package top.gaoyuanwang.gulimall.member.dao;

import top.gaoyuanwang.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author gaoyuanwang
 * @email wanggaouyuan@icloud.com
 * @date 2023-06-21 16:00:34
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
