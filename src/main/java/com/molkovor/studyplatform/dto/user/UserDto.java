package com.molkovor.studyplatform.dto.user;

import com.molkovor.studyplatform.entity.user.Role;

public record UserDto(Long id,
                      String name,
                      String email,
                      Role role) {
}
