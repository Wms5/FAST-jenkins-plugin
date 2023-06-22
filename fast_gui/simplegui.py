import PySimpleGUI as sg

layout=[
    #[sg.Image('fast-logo.png', size=(20, 1))],
    [sg.Text("Enter with the input:",background_color="black")],
    [sg.Input(key='input')],
    [sg.Text('Select subject:'), sg.Combo(['flex_v3', 'grep_v3', 'gzip_v1'], size=(20, 1), key='combo1')],
    [sg.Text('Select entity:'), sg.Combo(['function', 'line', 'branch'], size=(20, 1), key='combo2')],
    [sg.Text('Select subject:'), sg.Combo(['FAST-pw', 'FAST-one', 'FAST-log'], size=(20, 1), key='combo3')],
    [sg.Button("OK")]
    ]

window = sg.Window(title="FAST",layout=layout,background_color="black")

while True:
    event,values=window.read()

    if event == sg.WIN_CLOSED:
        break

    elif event == 'OK':
        combo1_value = values['combo1']
        combo2_value = values['combo2']
        combo3_value = values['combo3']
        input = values['input']
        print(f'Combo 1: {combo1_value}')
        print(f'Combo 2: {combo2_value}')
        print(f'Combo 3: {combo3_value}')
        print(f'input: {input}')
        break

window.close()