import math

separator = ';'
factor = 0.30
#name_file_input = "route_rectangle_128wpt.txt"
#name_file_input = "route_triangle_48wpt.txt"
name_file_input = "route_circle_80wpt2.txt"
name_file_output = "output-simplifier.txt"

'''
Abre o arquivo e o transforma em uma lista.
'''
def open_file(path_file):
	arq = open(path_file, 'r')
	lines = []
	for line in arq:
		lines.append(line)
	arq.close
	return lines

'''
Separa o conteudo da lista em um vetor para facilitar manuseio.
'''
def split_vector(vector, separator):
	size = len(vector)
	linhas_separadas1 = [-1] * size
	linhas_separadas2 = [-1] * size
	for i in range(size):
		linhas_separadas2[i] = vector[i].split(separator)
		linhas_separadas1[i] = [float(x) for x in linhas_separadas2[i]]
	return linhas_separadas1

'''
Calcula o valor da tangente que duas coordenadas diferentes apresentam.
'''
def tangent(coord):
	y = coord[1]
	x = coord[0]
	return math.atan2(y,x)

'''
Calcula o valor da diferenca entre dois pontos consecutivos.
'''
def difference(w_prev, w_next):
	diff = []
	diff.append(w_prev[0] - w_next[0])
	diff.append(w_prev[1] - w_next[1])
	return diff

'''
Funcao onde acontece de fato a simplificacao dos waypoints.
'''
def route_simplifier(coords):
	path_simplifier = []
	for i in range(len(coords)):
		if i == 0:
			path_simplifier.append(coords[i])
		elif i == 1:
			diff_prev = tangent(difference(coords[i-1], coords[i]))
		elif i > 1:
			diff_next = tangent(difference(coords[i-1], coords[i]))
			if abs(diff_next - diff_prev) > float(factor):
				path_simplifier.append(coords[i-1])
				diff_prev = diff_next
	return path_simplifier

'''
Apaga o que estava no arquivo de saida antigo e coloca as novas informacoes.
'''
def print_results(path_file, final_route, separator):
	text = []
	arq = open(path_file, 'w')
	for i in range(len(final_route)):
		text.append(str(final_route[i][0]))
		text.append(separator)
		text.append(str(final_route[i][1]))
		text.append(separator)
		text.append(str(final_route[i][2]))
		text.append("\n")
	arq.writelines(text)
	arq.close()

if __name__ == "__main__":
	arq = open_file(name_file_input)
	arq_split = split_vector(arq, separator)
	final_route = route_simplifier(arq_split)
	print_results(name_file_output, final_route, separator)
