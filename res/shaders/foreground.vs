#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoords;

out vec3 aColor;
out vec2 texCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform vec2 uvCoords;
uniform vec2 uvScale;
uniform vec2 offsets[9];

void main() {
        vec2 offset = offsets[gl_InstanceID];
        vec4 pos = model * vec4(aPos, 1.0);
        pos = pos + vec4(offset, 0.0, 0.0);
	gl_Position = projection * view * pos;
	aColor = aPos;
	texCoords = aTexCoords * uvScale + uvCoords * uvScale;
}