#namespace("test")
  #sql("selectOne")
    select 1
  #end
#end

#namespace("subject")
  #sql("slectAllIts")
    SELECT
    	s2.id,
    	i.abbr_name as i,
    	s.t,
    	s.name,
    	s2.s
    FROM
    	semester AS s
    	LEFT JOIN institution AS i ON s.institution_id = i.id
    	left join SUBJECT s2 on s.id=s2.semester_id
    WHERE
    	s.deleted = 0
    	AND i.deleted =0
    	and s2.deleted = 0
  #end
    #sql("slectItsByI")
      SELECT
      	s2.id,
      	i.abbr_name as i,
      	s.t,
      	s.name,
      	s2.s
      FROM
      	semester AS s
      	LEFT JOIN institution AS i ON s.institution_id = i.id
      	left join SUBJECT s2 on s.id=s2.semester_id
      WHERE
      	s.deleted = 0
      	AND i.deleted =0
      	and s2.deleted = 0
      	and i.abbr_name=?
    #end
#end

